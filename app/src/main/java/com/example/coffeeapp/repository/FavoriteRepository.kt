package com.example.coffeeapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val databaseReference: DatabaseReference
){
    fun getFavoriteItems(userId: String): LiveData<List<CoffeeResponseModel>> {
        val favoriteItemsLiveData = MutableLiveData<List<CoffeeResponseModel>>()
        val userRef = databaseReference.child("users").child(userId).child("favorite")
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoriteList: MutableList<CoffeeResponseModel> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    item?.let { favoriteList.add(it) }
                }
                favoriteItemsLiveData.postValue(favoriteList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled: favoriteRepository")
            }
        })
        return favoriteItemsLiveData
    }
}