package com.example.ideamagixassignment.shopapp.presentation.cart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import com.example.ideamagixassignment.shopapp.redux.Store
import com.example.ideamagixassignment.shopapp.redux.reducer.UiProductListReducer
import com.example.ideamagixassignment.shopapp.redux.updater.UiProductFavoriteUpdater
import com.example.ideamagixassignment.shopapp.redux.updater.UiProductInCartUpdater
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
) : ViewModel() {}