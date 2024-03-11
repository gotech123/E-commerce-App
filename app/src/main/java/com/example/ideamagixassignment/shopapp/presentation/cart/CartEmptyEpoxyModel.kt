package com.example.ideamagixassignment.shopapp.presentation.cart

import android.view.View
import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelCartEmptyBinding

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener(onClick)
    }
}
