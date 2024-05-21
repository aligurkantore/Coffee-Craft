package com.example.coffeeapp.models.order

import com.example.coffeeapp.models.coffee.CoffeeResponseModel

data class OrderModel(
    val orderId: String = "",
    val orderDate: Long = 0L,
    val orderTotal: Double = 0.0,
    val coffeeList: List<CoffeeResponseModel> = listOf()
){
    val totalPrice: Double
        get() = coffeeList.sumByDouble { (it.price?.times(it.count)) ?: 0.0 }
}