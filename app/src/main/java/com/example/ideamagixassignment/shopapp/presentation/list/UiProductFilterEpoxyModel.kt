package com.example.ideamagixassignment.shopapp.presentation.list

import androidx.core.content.ContextCompat
import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.ideamagixassignment.shopapp.models.domain.Filter
import com.example.ideamagixassignment.shopapp.models.ui.UiFilter
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelProductFilterBinding

data class UiProductFilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterSelected: (Filter) -> Unit
) : ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter) {

    override fun EpoxyModelProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(uiFilter.filter) }
        filterNameTextView.text = uiFilter.filter.displayText

        val cardBackGroundColorRes = if (uiFilter.isSelected) {
            R.color.purple_500
        } else {
            R.color.purple_200
        }
        root.setCardBackgroundColor(ContextCompat.getColor(root.context, cardBackGroundColorRes))
    }

}