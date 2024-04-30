package com.example.coffeeapp.models.login

data class Register(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirm_password: String? = null
)
