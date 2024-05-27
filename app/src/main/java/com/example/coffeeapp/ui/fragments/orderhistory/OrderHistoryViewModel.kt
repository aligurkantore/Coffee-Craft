package com.example.coffeeapp.ui.fragments.orderhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.order.OrderModel
import com.example.coffeeapp.repository.OrderHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderHistoryRepository: OrderHistoryRepository
) : BaseViewModel() {

    private val _orderHistoryLiveData = MutableLiveData<List<OrderModel>?>()
    val orderHistoryLiveData: LiveData<List<OrderModel>?> = _orderHistoryLiveData

    init {
        userId?.let { orderHistoryListener(it)}
    }

    private fun orderHistoryListener(userId: String){
        orderHistoryRepository.getOrderHistoryList(userId).observeForever {
            _orderHistoryLiveData.postValue(it)
        }
    }

}