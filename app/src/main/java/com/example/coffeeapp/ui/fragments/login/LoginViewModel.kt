package com.example.coffeeapp.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.login.Login
import com.example.coffeeapp.util.containsTurkishCharacters
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private val _resetPassword = MutableLiveData<String>()
    val resetPassword: LiveData<String> = _resetPassword

    fun loginUser(login: Login) {
        login.apply {
            if (email?.isNotEmpty() == true && password?.isNotEmpty() == true &&
                !email.containsTurkishCharacters()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    _login.value = it.isSuccessful
                }
            }else _login.value = false
        }
    }

    fun resetPassword(email: String) {
        if (email.isNotEmpty()) {
            viewModelScope.launch {
                auth.sendPasswordResetEmail(email)
                    .addOnFailureListener { exception ->
                        _resetPassword.value =
                            exception.message ?: "An error occurred while sending reset link."
                    }
            }
        } else {
            _resetPassword.value = "Please provide an email address."
        }
    }
}