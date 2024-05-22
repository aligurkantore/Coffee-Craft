package com.example.coffeeapp.ui.fragments.favorite

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

class FavoriteViewModel : BaseViewModel() {

    private lateinit var userRef: DatabaseReference
    private var authStateListener: FirebaseAuth.AuthStateListener

    val authStateLiveData = MutableLiveData<Boolean>()

    private val _favoriteItemsLiveData = MutableLiveData<List<CoffeeResponseModel>>()
    val favoriteItemsLiveData: LiveData<List<CoffeeResponseModel>> = _favoriteItemsLiveData


    init {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            user?.let {
                userRef = database.getReference("users/${it.uid}/favorite")
                getFavoriteItems()
                authStateLiveData.postValue(true)
            } ?: run {
                _favoriteItemsLiveData.postValue(emptyList())
                authStateLiveData.postValue(false)
            }
        }
    }

    private fun getFavoriteItems() {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList: MutableList<CoffeeResponseModel> = mutableListOf()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(CoffeeResponseModel::class.java)
                    item?.let { tempList.add(it) }
                }
                _favoriteItemsLiveData.postValue(tempList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("agt", "onCancelled: favoriteViewModel")
            }
        })
    }

    fun startAuthStateListener() {
        auth.addAuthStateListener(authStateListener)
    }

    fun stopAuthStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }
}