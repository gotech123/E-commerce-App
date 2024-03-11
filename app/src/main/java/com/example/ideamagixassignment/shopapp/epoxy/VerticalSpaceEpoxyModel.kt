package com.example.ideamagixassignment.shopapp.epoxy

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.PX
import com.example.namespace.R
import com.example.namespace.databinding.EpoxyModelVerticalSpaceBinding


data class VerticalSpaceEpoxyModel(
    @Dimension(unit = PX) val height: Int
) : ViewBindingKotlinModel<EpoxyModelVerticalSpaceBinding>(R.layout.epoxy_model_vertical_space) {

    override fun EpoxyModelVerticalSpaceBinding.bind() {
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, height
        )
    }
}