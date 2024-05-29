package com.example.coffeeapp

import android.app.Application
import com.example.coffeeapp.base.BaseShared
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        BaseShared.getSharedPreferences(this)
    }
}