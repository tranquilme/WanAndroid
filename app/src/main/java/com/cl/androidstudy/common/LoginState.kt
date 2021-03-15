package com.cl.androidstudy.common

import android.content.Context
import android.content.SharedPreferences
import com.cl.androidstudy.MyApplication

/**
 *@author clll
 *@date   2021/3/10 9:56
 */
object LoginState {
    val preferences: SharedPreferences = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
    val editor = preferences.edit()
}