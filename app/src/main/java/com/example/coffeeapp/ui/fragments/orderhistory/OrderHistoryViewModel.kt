package com.example.coffeeapp.ui.fragments.orderhistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.models.order.OrderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class OrderHistoryViewModel : BaseViewModel() {

    private val databaseRef: DatabaseReference = database.getReference("users/$userId/orderHistory")

    private val _orderHistoryLiveData = MutableLiveData<List<OrderModel>?>()
    val orderHistoryLiveData: LiveData<List<OrderModel>?> = _orderHistoryLiveData

    init {
        getOrderHistory()
    }

    private fun getOrderHistory() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val orderHistoryList = mutableListOf<OrderModel>()
                for (snapShot in dataSnapshot.children) {
                    val value = snapShot.getValue(OrderModel::class.java)
                    if (value != null) {
                        orderHistoryList.add(value)
                    }
                }
                _orderHistoryLiveData.postValue(orderHistoryList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("agt", "onCancelled: database error")
            }
        })
    }

}