package com.example.ideamagixassignment.shopapp.hilt

import retrofit2.Response
import retrofit2.http.GET
import com.example.ideamagixassignment.shopapp.models.network.NetworkProduct

interface ProductService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}