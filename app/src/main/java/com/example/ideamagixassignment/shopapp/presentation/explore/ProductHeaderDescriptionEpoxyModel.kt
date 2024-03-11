package com.example.ideamagixassignment.shopapp.presentation.explore


import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.ideamagixassignment.shopapp.models.ui.UiProduct
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelExploreProductHeaderBinding
import java.text.NumberFormat

data class ProductHeaderDescriptionEpoxyModel(
    val uiProduct: UiProduct
): ViewBindingKotlinModel<EpoxyModelExploreProductHeaderBinding>(
    R.layout.epoxy_model_explore_product_header
) {
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelExploreProductHeaderBinding.bind() {
        productTitleTextView.text = uiProduct.product.title
        productDescriptionTextView.text = uiProduct.product.description
        productCategoryTextView.text = uiProduct.product.category
        productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)
    }
}