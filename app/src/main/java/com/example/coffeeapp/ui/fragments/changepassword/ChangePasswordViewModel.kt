package com.example.coffeeapp.ui.fragments.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.google.firebase.auth.EmailAuthProvider

class ChangePasswordViewModel : BaseViewModel(){

    private val _passwordChangeLiveData = MutableLiveData<Boolean>()
    val passwordChangeLiveData: LiveData<Boolean> = _passwordChangeLiveData

    fun changePassword(oldPassword: String, newPassword: String) {
        val user = auth.currentUser
        if (user != null) {
            val credential = auth.currentUser?.email?.let { email ->
                EmailAuthProvider.getCredential(email, oldPassword)
            }
            if (credential != null) {
                user.reauthenticate(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            user.updatePassword(newPassword)
                                .addOnCompleteListener { updatePasswordResult ->
                                    if (updatePasswordResult.isSuccessful) {
                                        _passwordChangeLiveData.postValue(true)
                                    } else {
                                        _passwordChangeLiveData.postValue(false)
                                    }
                                }
                        } else {
                            _passwordChangeLiveData.postValue(false)
                        }
                    }
            } else {
                _passwordChangeLiveData.postValue(false)
            }
        } else {
            _passwordChangeLiveData.postValue(false)
        }
    }
}