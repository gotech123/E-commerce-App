package com.example.ideamagixassignment.shopapp.models.domain

data class User(
    val id: Int,
    val name: Name,
    val username: String,
    val phoneNumber: String,
    val address: Address,
    val email: String
)