package com.example.ideamagixassignment.shopapp.presentation.cart

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load

import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.ideamagixassignment.shopapp.models.ui.UiProductCart
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelCartItemBinding
import java.math.BigDecimal
import java.text.NumberFormat

data class CartItemEpoxyModel(
    val uiProductInCart: UiProductCart,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int,
    private val onFavoriteIconClicked: () -> Unit,
    private val onQuantityChanged: (Int) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartItemBinding>(R.layout.epoxy_model_cart_item) {

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelCartItemBinding.bind() {
        swipeToDismissTextView.translationX = 0f

        // Setup text
        val totalPrice = uiProductInCart.uiProduct.product.price * BigDecimal(uiProductInCart.quantity)
        productTitleTextView.text = uiProductInCart.uiProduct.product.title
        productPriceTextView.text = currencyFormatter.format(totalPrice)

        //Favorite icon
        val imageRes = if (uiProductInCart.uiProduct.isFavorite) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener {
            onFavoriteIconClicked()
        }

        //Load image
        productImageView.load(data = uiProductInCart.uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }

        quantityView.apply {
            quantityTextView.text = uiProductInCart.quantity.toString()
            minusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity - 1) }
            plusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity + 1) }
        }
    }
}
