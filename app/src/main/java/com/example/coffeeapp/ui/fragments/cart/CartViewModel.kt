package com.example.coffeeapp.ui.fragments.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.usecase.cart.CartUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase
) : BaseViewModel() {

    private var authStateListener: FirebaseAuth.AuthStateListener

    val authStateLiveData = MutableLiveData<Boolean>()

    private val _cartItemsLiveData = MutableLiveData<List<CoffeeResponseModel>>()
    val cartItemsLiveData: LiveData<List<CoffeeResponseModel>> = _cartItemsLiveData


    init {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            user?.let {
                cartItemsListener(it.uid)
                authStateLiveData.postValue(true)
            } ?: run {
                _cartItemsLiveData.postValue(emptyList())
                authStateLiveData.postValue(false)
            }
        }
        startAuthStateListener()
    }

    private fun cartItemsListener(userId: String) {
        cartUseCase.getCartItems(userId).observeForever {
            _cartItemsLiveData.postValue(it)
        }
    }

    private fun startAuthStateListener() {
        auth.addAuthStateListener(authStateListener)
    }

    private fun stopAuthStateListener() {
        auth.removeAuthStateListener(authStateListener)
    }

    override fun onCleared() {
        super.onCleared()
        stopAuthStateListener()
    }
}