package com.example.ideamagixassignment.shopapp.presentation.list

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load

import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.ideamagixassignment.shopapp.models.ui.UiProduct
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelItemBinding
import java.text.NumberFormat
import kotlin.math.roundToInt

data class UiProductEpoxyModel(
    val uiProduct: UiProduct?,
    val onFavoriteIconClicked: (Int) -> Unit,
    val onUiProductClicked: (Int) -> Unit,
    val onAddCartClicked: (Int) -> Unit,
) : ViewBindingKotlinModel<EpoxyModelItemBinding>(R.layout.epoxy_model_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let { uiProduct ->
            shimmerLayout.stopShimmer()

            //Setup text
            productTitleTextView.text = uiProduct.product.title
            productDescriptionTextView.text = uiProduct.product.description
            productCategoryTextView.text = uiProduct.product.category
            productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)

            //Expanded state
            productDescriptionTextView.isVisible = uiProduct.isExpanded
            root.setOnClickListener { onUiProductClicked(uiProduct.product.id) }

            //Favorite icon
            val imageRes = if (uiProduct.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            favoriteImageView.setIconResource(imageRes)
            favoriteImageView.setOnClickListener {
                onFavoriteIconClicked(uiProduct.product.id)
            }

            //In Cart status
            inCartView.isVisible = uiProduct.isInCart
            addToCartButton.setOnClickListener {
                onAddCartClicked(uiProduct.product.id)
            }

            //Load image
            imgProgressBar.isVisible = true
            productImageView.load(
                data = uiProduct.product.image
            ) {
                listener { request, result ->
                    imgProgressBar.isGone = true
                }
            }

            //Rating
            ratingIndicator.progress = (uiProduct.product.rating.value * 10).roundToInt()
            ratingTextView.text = "${uiProduct.product.rating.value}"

        } ?: shimmerLayout.startShimmer()
    }
}

private fun Int.roundToInt(): Int {
    TODO("Not yet implemented")
}
