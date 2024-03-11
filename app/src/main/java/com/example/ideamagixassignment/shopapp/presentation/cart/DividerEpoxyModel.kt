package com.example.ideamagixassignment.shopapp.presentation.cart

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.PX
import androidx.core.view.updateLayoutParams

import com.example.ideamagixassignment.shopapp.epoxy.ViewBindingKotlinModel
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelDividerBinding

data class DividerEpoxyModel(
    @Dimension(unit = PX) private val horizontalMargin: Int = 0,
    @Dimension(unit = PX) private val verticalMargin: Int = 0
) : ViewBindingKotlinModel<EpoxyModelDividerBinding>(R.layout.epoxy_model_divider) {

    override fun EpoxyModelDividerBinding.bind() {
        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        }
    }
}
