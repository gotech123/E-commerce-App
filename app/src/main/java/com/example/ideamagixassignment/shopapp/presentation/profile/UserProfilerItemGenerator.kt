package com.example.ideamagixassignment.shopapp.presentation.profile

import androidx.annotation.DrawableRes
import com.example.ideamagixassignment.shopapp.models.domain.User
import com.example.ideamagixassignment.shopapp.utils.ResourceProvider
import com.example.namespace.R
import javax.inject.Inject

class UserProfilerItemGenerator @Inject constructor(private val resourceProvider: ResourceProvider) {

    data class UserProfileUiItem(
        @DrawableRes val iconRes: Int,
        val headerText: String,
        val infoText: String
    )

    fun buildItems(user: User): List<UserProfileUiItem> {
        return buildList {
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_person_24,
                    headerText = resourceProvider.getString(R.string.username),
                    infoText = user.username
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_phone_24,
                    headerText = resourceProvider.getString(R.string.phone_number),
                    infoText = user.phoneNumber
                )
            )
            add(
                UserProfileUiItem(
                    iconRes = R.drawable.ic_round_location_on_24,
                    headerText = resourceProvider.getString(R.string.location),
                    infoText = "${user.address.street}, ${user.address.city}, ${user.address.zipcode}"
                )
            )
        }
    }
}