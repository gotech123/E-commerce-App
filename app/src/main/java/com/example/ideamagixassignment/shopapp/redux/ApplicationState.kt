package com.example.ideamagixassignment.shopapp.redux

import com.example.ideamagixassignment.shopapp.models.domain.Filter
import com.example.ideamagixassignment.shopapp.models.domain.Product
import com.example.ideamagixassignment.shopapp.models.domain.User

data class ApplicationState(
    val authState: AuthState = AuthState.UnAuth(),
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val inCartProducts: Set<Int> = emptySet(),
    val cartQuantitiesMap: Map<Int, Int> = emptyMap(), // productId -> quantity
    val explorePageMetadata: ExplorePageMetadata = ExplorePageMetadata()
) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )

    data class ExplorePageMetadata(
        val selectedProductId: Int = 0
    )

    sealed interface AuthState {
        data class Auth(val user: User) : AuthState
        data class UnAuth(val errorString: String? = null) : AuthState

        fun getGreetingMessage(): String {
            return if (this is Auth) {
                user.name.firstname
            } else {
                ""
            }
        }

        fun getEmail(): String {
            return if (this is Auth) {
                user.email
            } else {
                ""
            }
        }
    }
}