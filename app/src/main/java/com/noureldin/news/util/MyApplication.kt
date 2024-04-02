package com.noureldin.news.util

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.noureldin.news.database.MyDataBase
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MyDataBase.init(this)
        setLocaleFromPreference(this)
    }
    companion object {
        fun setLocaleFromPreference(context: Context) {
            val languageCode = getLanguagePreference(context)
            setLocale(context, languageCode)
        }

        private fun getLanguagePreference(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            return sharedPreferences.getString("language_code", null)
        }

        private fun setLocale(context: Context, languageCode: String?) {
            val locale = if (!languageCode.isNullOrBlank()) {
                Locale(languageCode)
            } else {
                Locale.getDefault()
            }
            Locale.setDefault(locale)

            val configuration = Configuration(context.resources.configuration)
            configuration.setLocale(locale)

            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
    }
}