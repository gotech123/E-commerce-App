package com.example.ideamagixassignment.shopapp.presentation.list

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import kotlinx.coroutines.launch
import com.example.ideamagixassignment.shopapp.models.domain.Filter
import java.util.UUID

class UiProductEpoxyController(
    private val viewModel: ProductListViewModel
) : TypedEpoxyController<ProductsListFragmentUiState>() {

    override fun buildModels(data: ProductsListFragmentUiState?) {

        when (data) {
            is ProductsListFragmentUiState.Success -> {
                val uiFilterModels = data.filter.map { uiFilter ->
                    UiProductFilterEpoxyModel(
                        uiFilter = uiFilter,
                        onFilterSelected = ::onFilterSelected
                    ).id(uiFilter.filter.value)
                }
                CarouselModel_()
                    .models(uiFilterModels)
                    .padding(Carousel.Padding(16, 8))
                    .id("filters")
                    .addTo(this)

                data.products.forEach { uiProduct ->
                    UiProductEpoxyModel(
                        uiProduct = uiProduct,
                        onFavoriteIconClicked = ::onFavoriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked,
                        onAddCartClicked = ::onAddCartClicked
                    ).id(uiProduct.product.id)
                        .addTo(this)
                }
            }
            is ProductsListFragmentUiState.Loading -> {
                repeat(7) {
                    val epoxyId = UUID.randomUUID().toString()
                    UiProductEpoxyModel(
                        uiProduct = null,
                        onFavoriteIconClicked = ::onFavoriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked,
                        onAddCartClicked = ::onAddCartClicked
                    ).id(epoxyId).addTo(this)
                }
            }
            else -> {
                throw RuntimeException("Unhandled branch! $data")
            }
        }
    }

    private fun onFavoriteIconClicked(selectedProductId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductFavoriteUpdater.onProductFavorite(
                    productId = selectedProductId, currentState = currentState
                )
            }
        }
    }

    private fun onUiProductClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentExpendedIds = currentState.expandedProductIds
                val newExpendedIds = if (currentExpendedIds.contains(productId)) {
                    currentExpendedIds.filter { it != productId }.toSet()
                } else {
                    currentExpendedIds + setOf(productId)
                }
                return@update currentState.copy(expandedProductIds = newExpendedIds)
            }
        }
    }

    private fun onFilterSelected(filter: Filter) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentSelectedFilter = currentState.productFilterInfo.selectedFilter
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentSelectedFilter != filter) filter else null
                    )
                )
            }
        }
    }

    private fun onAddCartClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                return@update viewModel.uiProductInCartUpdater.update(
                    productId = productId, currentState = currentState
                )
            }
        }
    }
}