package com.example.coffeeapp.util

import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

object ObjectUtil {

    fun updateAppBarTitle(activity: AppCompatActivity, title: String) {
        activity.supportActionBar?.title = title
    }

    fun generateOrderId(): String {
        return UUID.randomUUID().toString()
    }
}