package com.cl.androidstudy.common.interceptor

import android.content.Context
import android.util.Log
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.common.LoginState
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {    // 服务器返回数据时调用，获取cookies信息
        val originalResponse = chain.proceed(chain.request()) // 获取数据
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) { // 如果cookie信息不为空
            val cookies = HashSet<String>()
            for (data in originalResponse.headers("Set-Cookie")) {
                cookies.add(data)
            }
            val editor = LoginState.editor
            editor.putStringSet("cookies", cookies)
            editor.apply()
        }
        return originalResponse
    }

}