package com.example.pece2.utils

import android.app.Application
import android.content.Context

class AppConfig: Application() {
    companion object{
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        CONTEXT = applicationContext
        super.onCreate()
    }

}