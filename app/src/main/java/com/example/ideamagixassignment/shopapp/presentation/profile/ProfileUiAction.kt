package com.example.ideamagixassignment.shopapp.presentation.profile

import android.util.Log
import com.example.ideamagixassignment.shopapp.presentation.profile.auth.AuthViewModel
import com.example.namespace.R

class ProfileUiAction(private val viewModel: AuthViewModel) {

    fun onSignIn(userName: String, password: String) {
        viewModel.login(userName, password)
    }

    fun onProfileItemSelected(id: Int) {
        when (id) {
            R.drawable.ic_round_phone_24 -> viewModel.sendCallIntent()
            R.drawable.ic_round_location_on_24 -> viewModel.sendLocation()
            R.drawable.ic_round_logout_24 -> viewModel.logout()
            else -> {
                Log.i("SELECTION", "Unknown ID -> $id")
            }
        }
    }
}