package com.noureldin.news.util

import android.app.Application
import com.noureldin.news.database.MyDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MyDataBase.init(this)
    }
}