package com.example.ideamagixassignment.shopapp.presentation.list

import com.example.ideamagixassignment.shopapp.models.ui.UiFilter
import com.example.ideamagixassignment.shopapp.models.ui.UiProduct

sealed interface ProductsListFragmentUiState {
    data class Success(
        val filter: Set<UiFilter>,
        val products: List<UiProduct>
    ) : ProductsListFragmentUiState

    object Loading : ProductsListFragmentUiState
}
