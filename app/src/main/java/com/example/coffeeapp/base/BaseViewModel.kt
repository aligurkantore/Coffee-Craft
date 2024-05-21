package com.example.coffeeapp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private var job: Job? = null
    private var _error = MutableLiveData<String>()
    val userId = auth.currentUser?.uid
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    fun isLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }


    protected fun <T> performRequest(
        liveData: MutableLiveData<T>,
        request: suspend () -> Response<T>
    ){
        job = viewModelScope.launch(Dispatchers.IO){
            try {
                val response = request.invoke()
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        response.body()?.let {
                            liveData.postValue(it)
                        }
                    }else _error.postValue(response.message())
                }
            }catch (e: Exception){
                _error.postValue(e.message ?: "Unknown error occurred.")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}