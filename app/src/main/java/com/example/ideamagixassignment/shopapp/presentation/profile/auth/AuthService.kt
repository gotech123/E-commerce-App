package com.example.ideamagixassignment.shopapp.presentation.profile.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.ideamagixassignment.shopapp.models.network.LoginResponse
import com.example.ideamagixassignment.shopapp.models.network.NetworkUser
import com.example.ideamagixassignment.shopapp.models.network.post.LoginPostBody
interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body postBody: LoginPostBody
    ): Response<LoginResponse>

    @GET("users/{user-id}")
    suspend fun fetchUser(
        @Path("user-id") userId: Int
    ): Response<NetworkUser>

}