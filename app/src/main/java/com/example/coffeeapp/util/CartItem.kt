package com.example.coffeeapp.util

import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.coffeepacket.CoffeePacketResponseItem

sealed class CartItem {
    data class CoffeeItem(val coffeeResponseModel: CoffeeResponseModel) : CartItem()
    data class CoffeePacketItem(val coffeePacketResponseItem: CoffeePacketResponseItem) : CartItem()
}