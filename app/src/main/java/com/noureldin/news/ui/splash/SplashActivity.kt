package com.noureldin.news.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.noureldin.news.databinding.ActivitySplashBinding
import com.noureldin.news.ui.main.MainActivity
import org.xml.sax.ext.LexicalHandler

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private var sharedPreferences: SharedPreferences?=null

    lateinit var handler: Handler
    lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        switchNightMode()

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            startHomeActivity()
        }
            handler.postDelayed(runnable,2000)

    }

    private fun startHomeActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private fun switchNightMode() {

        sharedPreferences= getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode=sharedPreferences?.getBoolean("night",false)!!

        if (nightMode && !isDark()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
//        else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }

    }
    private fun isDark(): Boolean {
        return this.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}