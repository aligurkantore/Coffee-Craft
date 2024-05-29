package com.example.coffeeapp.base

import android.content.Context
import android.content.SharedPreferences
import com.example.coffeeapp.util.Constants.Companion.PREFS_NAME

object BaseShared {

     fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        getSharedPreferences(context).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean): Boolean {
        return getSharedPreferences(context).getBoolean(key, defaultValue)
    }

    fun saveString(context: Context, key: String, value: String) {
        getSharedPreferences(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String): String? {
        return getSharedPreferences(context).getString(key, defaultValue)
    }

    fun removeKey(context: Context, key: String) {
        getSharedPreferences(context).edit().remove(key).apply()
    }

    fun saveInt(context: Context, key: String, value: Int) {
        getSharedPreferences(context).edit().putInt(key, value).apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        return getSharedPreferences(context).getInt(key, defaultValue)
    }

    fun saveDouble(context: Context, key: String, value: Double) {
        return getSharedPreferences(context).edit().putString(key, value.toString()).apply()
    }

    fun getDouble(context: Context, key: String, defaultValue: Double): Double {
        val stringValue = getSharedPreferences(context).getString(key, defaultValue.toString())
        return stringValue?.toDoubleOrNull() ?: defaultValue
    }

}