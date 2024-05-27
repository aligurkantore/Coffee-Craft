package com.example.coffeeapp.ui.activities.second

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeeapp.base.BaseShared
import com.example.coffeeapp.databinding.ActivitySplashBinding
import com.example.coffeeapp.ui.activities.main.MainActivity
import com.example.coffeeapp.util.Constants.Companion.EN
import com.example.coffeeapp.util.Constants.Companion.LANG
import com.example.coffeeapp.util.changeLanguage
import com.example.coffeeapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        setUpBinding()
        setUpAnimation()
    }

    private fun setUpBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUpAnimation() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                delay(1000)
                textSplash1.visible()
                textSplash2.visible()
                delay(2000)
                buttonStarted.visible()
            }
        }

        binding.buttonStarted.setOnClickListener {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setLanguage() {
        val language = BaseShared.getString(this, LANG, EN)
        if (language != null) {
            changeLanguage(language)
        }
    }
}