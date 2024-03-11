package com.example.ideamagixassignment.shopapp.redux.updater

import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import javax.inject.Inject

class UiProductFavoriteUpdater @Inject constructor() {

    fun onProductFavorite(productId: Int, currentState: ApplicationState): ApplicationState {
        val currentFavoriteIds = currentState.favoriteProductIds
        val newFavoriteIds = if (currentFavoriteIds.contains(productId)) {
            currentFavoriteIds - productId
        } else {
            currentFavoriteIds + productId
        }
        return currentState.copy(favoriteProductIds = newFavoriteIds)
    }
}