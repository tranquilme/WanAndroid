package com.cl.androidstudy.logic.network

import com.cl.androidstudy.common.interceptor.AddCookiesInterceptor
import com.cl.androidstudy.common.interceptor.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val clientLogin = OkHttpClient.Builder()
        .addInterceptor(ReceivedCookiesInterceptor())
        .build()

    val retrofitLogin = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientLogin)
        .build()

    val clientCookie = OkHttpClient.Builder()
        .addInterceptor(AddCookiesInterceptor())
        .build()

    val retrofitCookie = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientCookie)
        .build()

    inline fun <reified T> create() = retrofit.create(T::class.java)

    inline fun <reified T> createLogin() = retrofitLogin.create(T::class.java)

    inline fun <reified T> createCookie() = retrofitCookie.create(T::class.java)
}