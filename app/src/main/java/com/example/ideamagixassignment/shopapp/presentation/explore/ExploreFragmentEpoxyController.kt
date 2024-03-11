package com.example.ideamagixassignment.shopapp.presentation.explore

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.TypedEpoxyController

class ExploreFragmentEpoxyController(
    private val actions: ExploreFragmentActions
) : TypedEpoxyController<ExploreFragmentViewState>() {

    override fun buildModels(data: ExploreFragmentViewState) = when (data) {
        ExploreFragmentViewState.Loading -> setupLoadingState()
        is ExploreFragmentViewState.Error -> setupErrorState(data)
        is ExploreFragmentViewState.Success -> setupSuccessState(data)
    }

    private fun setupLoadingState() {}

    private fun setupErrorState(state: ExploreFragmentViewState.Error) {}

    private fun setupSuccessState(state: ExploreFragmentViewState.Success) {
        HeaderImageEpoxyModel(state.selectedUiProduct.product.image)
            .id("header_image")
            .addTo(this)

        val circleImageEpoxyModels: List<EpoxyModel<*>> = state.allUiProducts.map { uiProduct ->
            CircleImageEpoxyModel(
                imageUrl = uiProduct.product.image,
                isSelected = uiProduct.product.id == state.selectedUiProduct.product.id,
                onClick = { actions.onProductSelected(uiProduct.product.id) }
            ).id("image_${uiProduct.product.id}")
        }

        CarouselModel_().models(circleImageEpoxyModels).id("carousel").addTo(this)

        ProductHeaderDescriptionEpoxyModel(state.selectedUiProduct)
            .id(state.selectedUiProduct.product.id)
            .addTo(this)

        ProductFooterEpoxyModel(
            quantity = state.quantity,
            uiProduct = state.selectedUiProduct,
            addToCart = {
                actions.onAddToCart(productId = state.selectedUiProduct.product.id)
            },
            onQuantityUpdate = { newQuantity ->
                actions.onQuantityChanged(
                    productId = state.selectedUiProduct.product.id,
                    quantity = newQuantity
                )
            }
        ).id("footer_${state.selectedUiProduct.product.id}").addTo(this)
    }
}