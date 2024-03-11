package com.example.ideamagixassignment.shopapp.models.mapper

import com.example.ideamagixassignment.shopapp.models.domain.Address
import com.example.ideamagixassignment.shopapp.models.domain.Name
import com.example.ideamagixassignment.shopapp.models.domain.User
import com.example.ideamagixassignment.shopapp.models.network.NetworkUser
import java.util.*
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun buildFrom(networkUser: NetworkUser): User {
        return User(
            id = networkUser.id,
            name = Name(
                firstname = networkUser.name.firstname.capitalize(),
                lastname = networkUser.name.lastname.capitalize()
            ),
            username = networkUser.username.capitalize(),
            phoneNumber = networkUser.phone,
            address = Address(
                city = networkUser.address.city,
                number = networkUser.address.number,
                street = networkUser.address.street,
                zipcode = networkUser.address.zipcode,
                lat = networkUser.address.geolocation.lat,
                long = networkUser.address.geolocation.long
            ),
            email = networkUser.email
        )
    }
}

private fun String.capitalize(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    }
}