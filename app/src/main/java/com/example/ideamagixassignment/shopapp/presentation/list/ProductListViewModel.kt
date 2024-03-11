package com.example.ideamagixassignment.shopapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.example.ideamagixassignment.shopapp.hilt.ProductRepository
import com.example.ideamagixassignment.shopapp.models.domain.Filter
import com.example.ideamagixassignment.shopapp.models.domain.Product
import com.example.ideamagixassignment.shopapp.redux.ApplicationState
import com.example.ideamagixassignment.shopapp.redux.Store
import com.example.ideamagixassignment.shopapp.redux.reducer.UiProductListReducer
import com.example.ideamagixassignment.shopapp.redux.updater.UiProductFavoriteUpdater
import com.example.ideamagixassignment.shopapp.redux.updater.UiProductInCartUpdater
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    val store: Store<ApplicationState>,
    private val filterGenerator: FilterGenerator,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
) : ViewModel() {

    fun refreshProducts() = viewModelScope.launch {
        if (store.read { it.products }.isNotEmpty()) return@launch
        val products: List<Product> = productRepository.fetchAllProducts()
        val filters: Set<Filter> = filterGenerator.generateFrom(products)
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products,
                productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = filters,
                    selectedFilter = applicationState.productFilterInfo.selectedFilter
                ),
                explorePageMetadata = ApplicationState.ExplorePageMetadata(
                    selectedProductId = products.getOrNull(0)?.id ?: 0
                )
            )
        }
    }
}