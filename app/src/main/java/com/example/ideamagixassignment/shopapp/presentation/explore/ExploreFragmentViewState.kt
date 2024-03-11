package com.example.ideamagixassignment.shopapp.presentation.explore

import com.example.ideamagixassignment.shopapp.models.ui.UiProduct

sealed class ExploreFragmentViewState {
    object Loading : ExploreFragmentViewState()
    data class Error(val errorMessage: String) : ExploreFragmentViewState()
    data class Success(
        val selectedUiProduct: UiProduct,
        val quantity: Int,
        val allUiProducts: List<UiProduct>
    ) : ExploreFragmentViewState()
}