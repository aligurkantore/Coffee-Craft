package com.example.coffeeapp.base

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun isLoggedIn(): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

/*
    protected fun <T> performRequest(
        liveData: MutableLiveData<T>,
        request: suspend () -> Response<T>
    ){
        job = viewModelScope.launch(Dispatchers.IO){
            isLoading.postValue(true)
            try {
                val response = request.invoke()
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        response.body()?.let {
                            liveData.postValue(it)
                        }
                    }else error.postValue(response.message())
                }
            }catch (e: Exception){
                error.postValue(e.message ?: "Unknown error occurred.")
            }finally {
                isLoading.postValue(false)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

 */
}