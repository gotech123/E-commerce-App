package com.example.ideamagixassignment.shopapp.redux.reducer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import com.example.ideamagixassignment.shopapp.models.ui.UiProduct
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import com.example.ideamagixassignment.shopapp.redux.Store
import javax.inject.Inject

class UiProductListReducer @Inject constructor() {

    fun reduce(store: Store<ApplicationState>): Flow<List<UiProduct>> {
        return combine(
            store.stateFlow.map { it.products },
            store.stateFlow.map { it.favoriteProductIds },
            store.stateFlow.map { it.expandedProductIds },
            store.stateFlow.map { it.inCartProducts }
        ) { listOfProducts, setOfFavoriteIds, setExpandedIds, inCartProductsIds ->

            if (listOfProducts.isEmpty()) {
                return@combine emptyList<UiProduct>()
            }

            return@combine listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isExpanded = setExpandedIds.contains(product.id),
                    isInCart = inCartProductsIds.contains(product.id)
                )
            }
        }
    }
}