package com.cl.androidstudy.ui.home.hot

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object HotRepository {
    fun getArticle(page: Int) = liveData(Dispatchers.IO){
        val res = try {
            val articleResponse = ApiNetwork.getArticle(page)
            if (articleResponse.errorCode == 0) {
                Result.success(articleResponse) // Result 类可以对结果进行封装，返回更加直观的信息
            } else {
                Result.failure(RuntimeException("errorCode is ${articleResponse.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<ArticleResponse>(e)
        }
        emit(res)
    }
}