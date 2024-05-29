package com.example.coffeeapp.usecase

import androidx.lifecycle.LiveData
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    fun getFavoriteItems(userId: String): LiveData<List<CoffeeResponseModel>>{
        return favoriteRepository.getFavoriteItems(userId)
    }
}