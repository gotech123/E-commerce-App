package com.example.ideamagixassignment.shopapp.presentation.cart

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import kotlinx.coroutines.launch
import com.example.ideamagixassignment.shopapp.epoxy.VerticalSpaceEpoxyModel
import com.example.ideamagixassignment.shopapp.extensions.toPX

class CartFragmentEpoxyController(
    private val viewModel: CartFragmentViewModel,
    private val onEmptyClicked: () -> Unit
) : TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data) {
            null, is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel(onClick = {
                    onEmptyClicked()

                }).id("empty_state").addTo(this)
            }
            is CartFragment.UiState.NonEmpty -> {
                data.products.forEachIndexed { index, uiProductInCart ->

                    addVerticalStyling(index)

                    CartItemEpoxyModel(
                        uiProductInCart = uiProductInCart,
                        horizontalMargin = 16.toPX(),
                        onFavoriteIconClicked = {
                            viewModel.viewModelScope.launch {
                                viewModel.store.update {
                                    return@update viewModel.uiProductFavoriteUpdater.onProductFavorite(
                                        productId = uiProductInCart.uiProduct.product.id,
                                        currentState = it
                                    )
                                }
                            }
                        },
                        onQuantityChanged = { newQuantity: Int ->
                            if (newQuantity < 1 || newQuantity > 99) return@CartItemEpoxyModel
                            viewModel.viewModelScope.launch {
                                viewModel.store.update { currentState ->
                                    val newMapEntry =
                                        uiProductInCart.uiProduct.product.id to newQuantity
                                    val newMap = currentState.cartQuantitiesMap + newMapEntry
                                    return@update currentState.copy(cartQuantitiesMap = newMap)
                                }
                            }
                        }
                    ).id(uiProductInCart.uiProduct.product.id).addTo(this)
                }
            }
        }
    }

    private fun addVerticalStyling(index: Int) {
        VerticalSpaceEpoxyModel(8.toPX()).id("top_space_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(horizontalMargin = 16.toPX()).id("divider_$index")
                .addTo(this)
        }

        VerticalSpaceEpoxyModel(8.toPX()).id("bottom_space_$index").addTo(this)
    }
}