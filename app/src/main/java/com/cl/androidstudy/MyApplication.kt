package com.cl.androidstudy

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MyApplication: Application(){
    companion object {
        lateinit var context: Context
        val TAG = "testTag"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}