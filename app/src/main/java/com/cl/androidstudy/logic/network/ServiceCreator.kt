package com.cl.androidstudy.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    inline fun <reified T> create() = retrofit.create(T::class.java)
}