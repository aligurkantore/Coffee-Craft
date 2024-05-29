package com.example.coffeeapp.usecase.orderHistory

import androidx.lifecycle.LiveData
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.repository.orderhistory.OrderHistoryRepository
import javax.inject.Inject

class OrderHistoryUseCase @Inject constructor(
    private val orderHistoryRepository: OrderHistoryRepository
) {
    fun getOrderHistory(userId: String): LiveData<List<OrderModel>>{
        return orderHistoryRepository.getOrderHistoryList(userId)
    }
}