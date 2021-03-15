package com.cl.androidstudy.ui.me.login

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.LoginResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

object LoginRepository {
    fun login(username: String, password: String) = liveData(Dispatchers.IO) {
        val res = try {
            val loginResult = ApiNetwork.login(username, password)
            Result.success(loginResult)
        } catch (e: Exception) {
            Result.failure<LoginResponse>(e)    //使用Result对结果进行封装，便于查错。同时还可以返回统一类型的结果而不是any，便于处理结果。
        }
        emit(res)
    }
}