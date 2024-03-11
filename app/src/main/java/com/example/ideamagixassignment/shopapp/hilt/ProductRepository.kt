package com.example.ideamagixassignment.shopapp.hilt

import com.example.ideamagixassignment.shopapp.models.domain.Product
import com.example.ideamagixassignment.shopapp.models.mapper.ProductMapper
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val productMapper: ProductMapper
) {

    suspend fun fetchAllProducts(): List<Product> {
        return productService.getAllProducts().body()?.let { networkProducts ->
            networkProducts.map { productMapper.buildFrom(it) }
        } ?: emptyList()
    }
}