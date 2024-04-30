package com.example.coffeeapp.util

import androidx.navigation.NavController
import com.example.coffeeapp.R

object NavigationManager {

    private var currentFragmentId: Int? = null

    fun setCurrentFragmentId(fragmentId: Int?) {
        currentFragmentId = fragmentId
    }

    fun navigateToLogin(navController: NavController) {
        currentFragmentId?.let { fragmentId ->
            val action = when (fragmentId) {
                R.id.cartFragment -> R.id.action_cartFragment_to_loginFragment
                R.id.favoriteFragment -> R.id.action_favoriteFragment_to_loginFragment
                R.id.paymentInformationFragment -> R.id.action_paymentInformationFragment_to_loginFragment
                else -> return
            }
            navController.navigate(action)
        }
    }

    fun navigateBackAfterLogin(navController: NavController) {
        currentFragmentId?.let { fragmentId ->
            navController.navigate(fragmentId)
            currentFragmentId = null
        }
    }
}
