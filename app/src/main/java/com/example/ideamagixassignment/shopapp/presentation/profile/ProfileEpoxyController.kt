package com.example.ideamagixassignment.shopapp.presentation.profile

import androidx.annotation.DrawableRes
import com.airbnb.epoxy.TypedEpoxyController
import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.ideamagixassignment.shopapp.extensions.toPX
import com.example.ideamagixassignment.shopapp.presentation.cart.DividerEpoxyModel
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import com.example.ideamagixassignment.shopapp.utils.ResourceProvider
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelProfileSignedInItemBinding
import com.example.namespace.databinding.EpoxyModelProfileSignedOutBinding
import javax.inject.Inject

class ProfileEpoxyController @Inject constructor(
    private val userProfilerGenerator: UserProfilerItemGenerator,
    private val profileUiAction: ProfileUiAction,
    private val resourceProvider: ResourceProvider
) : TypedEpoxyController<ApplicationState.AuthState>() {

    override fun buildModels(data: ApplicationState.AuthState) {

        when (data) {
            is ApplicationState.AuthState.UnAuth -> {
                SignedOutEpoxyModel(
                    onSignIn = { username, password ->
                        profileUiAction.onSignIn(username, password)
                    },
                    errorMessage = data.errorString
                ).id("signed_out_state").addTo(this)
            }
            is ApplicationState.AuthState.Auth -> {
                userProfilerGenerator.buildItems(user = data.user).forEach { profileItem ->
                    SignedInItemEpoxyModel(
                        iconRes = profileItem.iconRes,
                        headerText = profileItem.headerText,
                        infoText = profileItem.infoText,
                        onCLick = { profileUiAction.onProfileItemSelected(profileItem.iconRes) }
                    ).id(profileItem.iconRes).addTo(this)

                    DividerEpoxyModel(
                        horizontalMargin = 20.toPX()
                    ).id("divider_${profileItem.iconRes}").addTo(this)
                }

                SignedInItemEpoxyModel(
                    iconRes = R.drawable.ic_round_logout_24,
                    infoText = resourceProvider.getString(R.string.sign_out),
                    headerText = resourceProvider.getString(R.string.logout),
                    onCLick = { profileUiAction.onProfileItemSelected(R.drawable.ic_round_logout_24) }
                ).id(R.drawable.ic_round_logout_24).addTo(this)
            }
        }
    }

    data class SignedOutEpoxyModel(
        val onSignIn: (String, String) -> Unit,
        val errorMessage: String?
    ) : ViewBindingKotlinModel<EpoxyModelProfileSignedOutBinding>(R.layout.epoxy_model_profile_signed_out) {

        override fun EpoxyModelProfileSignedOutBinding.bind() {
            passwordLayout.error = errorMessage
            signInButton.setOnClickListener {
                val username = usernameEditText.text?.toString()
                val password = passwordEditText.text?.toString()
                if (username.isNullOrBlank() || password.isNullOrBlank()) {
                    passwordLayout.error = "Both fields required"
                    return@setOnClickListener
                }
                passwordLayout.error = null
                onSignIn(username, password)
            }
        }

        override fun EpoxyModelProfileSignedOutBinding.unbind() {
            usernameEditText.text = null
            usernameEditText.clearFocus()
            passwordEditText.text = null
            passwordEditText.clearFocus()
            passwordLayout.error = null
        }

    }

    data class SignedInItemEpoxyModel(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String,
        val onCLick: () -> Unit
    ) : ViewBindingKotlinModel<EpoxyModelProfileSignedInItemBinding>(R.layout.epoxy_model_profile_signed_in_item) {

        override fun EpoxyModelProfileSignedInItemBinding.bind() {
            iconImageView.setImageResource(iconRes)
            headerTextView.text = headerText
            infoTextView.text = infoText
            root.setOnClickListener { onCLick() }
        }
    }
}