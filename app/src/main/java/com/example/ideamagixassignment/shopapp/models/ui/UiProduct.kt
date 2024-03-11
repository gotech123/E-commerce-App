package com.example.ideamagixassignment.shopapp.models.ui

import com.example.ideamagixassignment.shopapp.models.domain.Product

data class UiProduct(
    val isFavorite: Boolean = false,
    val product: Product,
    val isExpanded: Boolean = false,
    val isInCart: Boolean = false
)