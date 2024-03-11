package com.example.ideamagixassignment.shopapp.presentation.cart

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyTouchHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.ideamagixassignment.shopapp.models.ui.UiProductCart
import com.example.namespace.R
import com.example.namespace.databinding.FragmentCartBinding
import java.math.BigDecimal
import java.text.NumberFormat
import kotlin.math.max

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CartFragmentViewModel>()

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = CartFragmentEpoxyController(viewModel, onEmptyClicked = {
            findNavController().navigate(R.id.action_cartFragment_to_productListFragment)
        })

        binding.epoxyRecyclerView.setController(epoxyController)

        val uiProductInCartFlow = viewModel.uiProductListReducer.reduce(
            store = viewModel.store
        ).map { uiProducts ->
            uiProducts.filter { it.isInCart }
        }

        combine(
            uiProductInCartFlow,
            viewModel.store.stateFlow.map { it.cartQuantitiesMap }
        ) { uiProducts, quantityMap ->
            uiProducts.map {
                UiProductCart(
                    uiProduct = it,
                    quantity = quantityMap[it.product.id] ?: 1
                )
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiProducts ->
            val viewState = if (uiProducts.isEmpty()) {
                UiState.Empty
            } else {
                UiState.NonEmpty(uiProducts)
            }
            epoxyController.setData(viewState)
            updateTotalLayout(uiProducts)
        }

        setupSwipeToDelete()

        binding.checkOutButton.setOnClickListener {

        }

    }

    private fun updateTotalLayout(uiProductsInCart: List<UiProductCart>) {
        val totalAmount =
            uiProductsInCart.sumOf { BigDecimal(it.quantity) * it.uiProduct.product.price }
        binding.totalDescription.text = getString(
            R.string.total_description,
            uiProductsInCart.size, currencyFormatter.format(totalAmount)
        )
        binding.checkOutButton.isEnabled = uiProductsInCart.isNotEmpty()
    }

    private fun setupSwipeToDelete() {
        EpoxyTouchHelper
            .initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(CartItemEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<CartItemEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    model?.let { epoxyModel ->
                        viewModel.viewModelScope.launch {
                            viewModel.store.update {
                                return@update viewModel.uiProductInCartUpdater.update(
                                    productId = epoxyModel.uiProductInCart.uiProduct.product.id,
                                    currentState = it
                                )
                            }
                        }
                    }
                }

                override fun onSwipeProgressChanged(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    swipeProgress: Float,
                    canvas: Canvas?
                ) {
                    itemView?.findViewById<View>(R.id.swipe_to_dismiss_text_view)?.apply {
                        translationX = max(-itemView.translationX, -measuredWidth.toFloat())
                        alpha = 5f * swipeProgress
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    sealed interface UiState {
        object Empty : UiState
        data class NonEmpty(val products: List<UiProductCart>) : UiState
    }
}