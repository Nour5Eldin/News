package com.noureldin.news.ui.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.noureldin.news.databinding.ActivitySplashBinding
import com.noureldin.news.ui.main.MainActivity
import com.noureldin.news.util.LocaleManager

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    lateinit var handler: Handler
    lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            startHomeActivity()
        }
            handler.postDelayed(runnable,2000)
        loadSavedSettings()
    }

    private fun startHomeActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private fun loadSavedSettings() {
        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)

        // Load language
        val languageCode = sharedPreferences.getString("language_code", "en")
        LocaleManager.setLocale(this, languageCode)

        // Load country code
        val countryCode = sharedPreferences.getString("country_code", "us")

        // Apply country-specific settings or configurations if needed

        // Load app mode
        val isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}