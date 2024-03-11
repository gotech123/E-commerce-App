package com.example.ideamagixassignment.shopapp.presentation.list

import com.example.ideamagixassignment.shopapp.models.domain.Filter
import com.example.ideamagixassignment.shopapp.models.domain.Product
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    fun generateFrom(productsList: List<Product>): Set<Filter> {
        return productsList.groupBy {
            it.category
        }.map { mapEntry ->
            Filter(value = mapEntry.key, displayText = "${mapEntry.key} (${mapEntry.value.size})")
        }.toSet()
    }
}