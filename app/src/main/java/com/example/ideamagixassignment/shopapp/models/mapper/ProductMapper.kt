package com.example.ideamagixassignment.shopapp.models.mapper

import com.example.ideamagixassignment.shopapp.models.domain.Product
import com.example.ideamagixassignment.shopapp.models.network.NetworkProduct
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun buildFrom(networkProduct: NetworkProduct): Product {
        return Product(
            category = capitalize(networkProduct.category),
            description = networkProduct.description,
            id = networkProduct.id,
            image = networkProduct.image,
            price = BigDecimal(networkProduct.price).setScale(2, RoundingMode.HALF_UP),
            title = networkProduct.title,
            rating = Product.Rating(
                value = networkProduct.rating.rate,
                rate = networkProduct.rating.count
            )
        )
    }

    private fun capitalize(sequence: String): String {
        return sequence.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}