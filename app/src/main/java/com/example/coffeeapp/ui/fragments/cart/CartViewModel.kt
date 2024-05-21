package com.example.coffeeapp.ui.fragments.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class CartViewModel : BaseViewModel() {

    private lateinit var userRef: DatabaseReference
    private var authStateListener: FirebaseAuth.AuthStateListener

    private val _cartItemsLiveData = MutableLiveData<List<CoffeeResponseModel>>()
    val cartItemsLiveData: LiveData<List<CoffeeResponseModel>> = _cartItemsLiveData


    init {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            user?.let {
                userRef = database.getReference("users/${it.uid}/cart")
                getCartItems()
            }
        }
    }

    fun startAuthStateListener() {
        auth.addAuthStateListener(authStateListener)
    }

    fun stopAuthStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    private fun getCartItems() {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: MutableList<CoffeeResponseModel> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    item?.let { tempList.add(it) }
                }
                _cartItemsLiveData.postValue(tempList)

            }


            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled: cartViewModel")
            }
        })
    }
}