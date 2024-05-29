package com.example.coffeeapp.repository.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val databaseReference: DatabaseReference
){
    fun getCartItems(userId: String): LiveData<List<CoffeeResponseModel>> {
        val cartItemsLiveData = MutableLiveData<List<CoffeeResponseModel>>()
        val userRef = databaseReference.child("users").child(userId).child("cart")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartList: MutableList<CoffeeResponseModel> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val value = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    value?.let { cartList.add(it) }
                }
                cartItemsLiveData.postValue(cartList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled: cartRepository")
            }
        })
        return cartItemsLiveData
    }
}