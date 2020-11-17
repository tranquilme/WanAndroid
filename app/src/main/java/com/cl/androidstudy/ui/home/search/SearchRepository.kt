package com.cl.androidstudy.ui.home.searchD

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.logic.network.ApiNetwork
import java.lang.Exception
import java.lang.RuntimeException

object SearchRepository {
    fun searchArticle(page: Int, keywords: String) = liveData {
        val res = try {
            val article = ApiNetwork.searchArticle(page, keywords)
            if (article.errorCode == 0) {
                Result.success(article.data.datas)
            }else {
                Result.failure(RuntimeException("errorCode is ${article.errorCode}"))
            }
        }catch (e: Exception) {
            Result.failure<List<Datas>>(e)
        }
        emit(res)
    }
}