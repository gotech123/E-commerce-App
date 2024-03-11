package com.example.ideamagixassignment.shopapp.redux.updater

import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import javax.inject.Inject

class UiProductInCartUpdater @Inject constructor() {

    fun update(productId: Int, currentState: ApplicationState): ApplicationState {
        val currentProductInCart = currentState.inCartProducts
        val newProductInCart = if (currentProductInCart.contains(productId)) {
            currentProductInCart - productId
        } else {
            currentProductInCart + productId
        }
        return currentState.copy(inCartProducts = newProductInCart)
    }
}