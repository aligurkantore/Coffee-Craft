package com.example.coffeeapp.ui.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.coffeeapp.R
import com.example.coffeeapp.databinding.ActivityMainBinding
import com.example.coffeeapp.ui.dialogs.CustomDialog
import com.example.coffeeapp.ui.fragments.cart.CartFragment
import com.example.coffeeapp.ui.fragments.favorite.FavoriteFragment
import com.example.coffeeapp.ui.fragments.home.HomeFragment
import com.example.coffeeapp.ui.fragments.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBinding()
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as NavHostFragment
        setUpNavigation()
    }

    private fun setUpBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpNavigation() {
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.visibility = when (destination.id) {
                R.id.homeFragment, R.id.cartFragment, R.id.favoriteFragment, R.id.profileFragment,
                -> View.VISIBLE

                else -> View.GONE
            }
        }

        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { menuItem ->
                NavigationUI.onNavDestinationSelected(menuItem, navController)
                navController.popBackStack(menuItem.itemId, false)
                true
            }
        }
    }

    override fun onBackPressed() {
        when (navHostFragment.childFragmentManager.fragments.firstOrNull()) {
            is HomeFragment -> showExitDialog()
            is CartFragment -> showExitDialog()
            is FavoriteFragment -> showExitDialog()
            is ProfileFragment -> showExitDialog()
            else -> super.onBackPressed()
        }
    }

    private fun showExitDialog() {
        CustomDialog(
            this,
            getString(R.string.exit),
            getString(R.string.confirm_exit_dialog),
            getString(R.string.yes),
            getString(R.string.no),
            positiveButtonClickListener = {
                finishAffinity()
            }
        ).show()
    }
}