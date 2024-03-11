package com.example.ideamagixassignment.shopapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.namespace.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductListViewModel by viewModels()

    @Inject
    lateinit var uiStateGenerator: ProductsListFragmentUiStateGenerator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = UiProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)

        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            viewModel.store.stateFlow.map { it.productFilterInfo },
        ) { uiProducts, productFilter ->
            uiStateGenerator.generate(uiProducts, productFilter)
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiProducts ->
            controller.setData(uiProducts)
        }
        viewModel.refreshProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}