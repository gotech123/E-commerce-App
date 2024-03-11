package com.example.ideamagixassignment.shopapp.presentation.explore

import android.widget.SeekBar
import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.ideamagixassignment.shopapp.models.ui.UiProduct
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelExploreProductFooterBinding
import kotlin.math.roundToInt

data class ProductFooterEpoxyModel(
    val quantity: Int,
    val uiProduct: UiProduct,
    val addToCart: () -> Unit,
    val onQuantityUpdate: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelExploreProductFooterBinding>(R.layout.epoxy_model_explore_product_footer) {

    override fun EpoxyModelExploreProductFooterBinding.bind() {
        ratingIndicator.progress = (uiProduct.product.rating.value * 10).roundToInt()
        ratingTextView.text = "${uiProduct.product.rating.value}"

        quantitySeekbar.setOnSeekBarChangeListener(null)
        quantitySeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                quantityTextView2.text = "$progress"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // Nothing to do
            }

            override fun onStopTrackingTouch(seekbar: SeekBar) {
                onQuantityUpdate(seekbar.progress)
            }
        })
        quantitySeekbar.progress = quantity
        quantityTextView2.text = "$quantity"

        val text = if (uiProduct.isInCart) "Remove from cart" else "Add to cart"
        addToCartButton.text = text
        addToCartButton.setOnClickListener { addToCart() }
    }
}

private fun Int.roundToInt(): Int {
    TODO("Not yet implemented")
}
