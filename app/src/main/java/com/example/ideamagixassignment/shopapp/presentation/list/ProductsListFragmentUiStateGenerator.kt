package com.example.ideamagixassignment.shopapp.presentation.list

import com.example.ideamagixassignment.shopapp.models.ui.UiFilter
import com.example.ideamagixassignment.shopapp.models.ui.UiProduct
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import javax.inject.Inject

class ProductsListFragmentUiStateGenerator @Inject constructor() {

    fun generate(
        uiProducts: List<UiProduct>,
        productFilter: ApplicationState.ProductFilterInfo
    ): ProductsListFragmentUiState {
        if (uiProducts.isEmpty()) {
            return ProductsListFragmentUiState.Loading
        }

        val uiFilters = productFilter.filters.map { filter ->
            UiFilter(
                filter = filter,
                isSelected = productFilter.selectedFilter?.equals(filter) == true
            )

        }.toSet()

        val filterProducts = if (productFilter.selectedFilter == null) {
            uiProducts
        } else {
            uiProducts.filter {
                it.product.category == productFilter.selectedFilter.value
            }
        }
        return ProductsListFragmentUiState.Success(uiFilters, filterProducts)
    }
}