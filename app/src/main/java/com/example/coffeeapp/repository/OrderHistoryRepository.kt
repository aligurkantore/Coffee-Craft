package com.example.coffeeapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.models.order.OrderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class OrderHistoryRepository @Inject constructor(
    private val databaseReference: DatabaseReference
) {

    fun getOrderHistoryList(userId: String):LiveData<List<OrderModel>>{
        val orderHistoryLiveData = MutableLiveData<List<OrderModel>>()
        val userRef = databaseReference.child("users").child(userId).child("orderHistory")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderHistoryList: MutableList<OrderModel> = mutableListOf()
                for (snapShot in snapshot.children) {
                    val value = snapShot.getValue(OrderModel::class.java)
                    value?.let { orderHistoryList.add(it) }
                }
                orderHistoryLiveData.postValue(orderHistoryList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return orderHistoryLiveData
    }
}