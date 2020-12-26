package com.cl.androidstudy.common.interceptor

import android.content.Context
import android.util.Log
import com.cl.androidstudy.MyApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddCookiesInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {    // 客户端发送请求时调用，往请求头中加入cookie
        val builder = chain.request().newBuilder()
        try {
            val cookies = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookies", null)
            if (cookies != null) {
                for (cookie in cookies) {
                    builder.addHeader("Cookie", cookie)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return chain.proceed(builder.build())
    }
}