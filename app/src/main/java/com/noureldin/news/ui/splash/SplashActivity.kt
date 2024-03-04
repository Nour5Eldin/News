package com.noureldin.news.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.noureldin.news.databinding.ActivitySplashBinding
import com.noureldin.news.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper())
            .postDelayed({
                startHomeActivity()
            },2000)
    }

    private fun startHomeActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}