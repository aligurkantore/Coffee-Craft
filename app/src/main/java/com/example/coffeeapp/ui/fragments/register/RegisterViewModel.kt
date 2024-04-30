package com.example.coffeeapp.ui.fragments.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coffeeapp.base.BaseViewModel
import com.example.coffeeapp.models.login.Register
import com.example.coffeeapp.util.containsTurkishCharacters

class RegisterViewModel : BaseViewModel() {

    private val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> = _register


    fun registerUser(register: Register) {
        register.apply {
            val nameValid = name?.isNotEmpty() == true
            val emailValid = email?.isNotEmpty() == true
            val passwordValid = password?.isNotEmpty() == true
            val confirmValid = confirm_password?.isNotEmpty() == true
            val passwordMatch = password == confirm_password
            val emailValidForRegistration = email?.let { !it.containsTurkishCharacters() } ?: true
            if (nameValid && emailValid && passwordValid && confirmValid && passwordMatch && emailValidForRegistration) {
                if (password != null) {
                    if (email != null) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                            _register.value = it.isSuccessful
                        }
                    }
                }
            } else _register.value = false
        }
    }

}