package com.example.ideamagixassignment.shopapp.presentation.profile.auth

import com.example.ideamagixassignment.shopapp.models.network.LoginResponse
import com.example.ideamagixassignment.shopapp.models.network.NetworkUser
import com.example.ideamagixassignment.shopapp.models.network.post.LoginPostBody
import retrofit2.Response

import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return authService.login(LoginPostBody(username, password))
    }

    suspend fun fetchDon(): Response<NetworkUser> {
        return authService.fetchUser(userId = 4)
    }

}