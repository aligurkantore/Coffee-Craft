package com.example.coffeeapp.ui.fragments.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.coffee.CoffeeResponseModel
import com.example.coffeeapp.usecase.FavoriteUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private var favoriteUseCase: FavoriteUseCase
) : BaseViewModel() {

    private var authStateListener: FirebaseAuth.AuthStateListener

    val authStateLiveData = MutableLiveData<Boolean>()

    private val _favoriteItemsLiveData = MutableLiveData<List<CoffeeResponseModel>>()
    val favoriteItemsLiveData: LiveData<List<CoffeeResponseModel>> = _favoriteItemsLiveData


    init {
        authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user: FirebaseUser? = firebaseAuth.currentUser
            user?.let {
                favoriteItemsListener(it.uid)
                authStateLiveData.postValue(true)
            } ?: run {
                _favoriteItemsLiveData.postValue(emptyList())
                authStateLiveData.postValue(false)
            }
        }
        startAuthStateListener()
    }

    private fun favoriteItemsListener(userId: String) {
        favoriteUseCase.getFavoriteItems(userId).observeForever {
            _favoriteItemsLiveData.postValue(it)
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