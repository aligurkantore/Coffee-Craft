package com.example.coffeeapp.usecase.cart

import androidx.lifecycle.LiveData
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.repository.cart.CartRepository
import javax.inject.Inject

class CartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    fun getCartItems(userId: String): LiveData<List<CoffeeResponseModel>>{
        return cartRepository.getCartItems(userId)
    }
}