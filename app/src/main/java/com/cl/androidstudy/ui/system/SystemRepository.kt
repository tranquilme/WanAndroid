package com.cl.androidstudy.ui.system

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.model.SystemKind
import com.cl.androidstudy.logic.network.ApiNetwork
import java.lang.Exception
import java.lang.RuntimeException

object SystemRepository {
    fun getSystemKind() = liveData {
        val res = try {
            val systemKind = ApiNetwork.getSystemKind()
            if (systemKind.errorCode == 0) {
                Result.success(systemKind)
            } else {
                Result.failure(RuntimeException("errorCode is ${systemKind.errorCode}"))
            }
        }catch (e: Exception) {
            Result.failure<SystemKind>(e)
        }
        emit(res)
    }

    fun getSystemArticle(page: Int, cid: Int) = liveData {
        val res = try {
            val systemArticle = ApiNetwork.getSystemArticle(page, cid)
            if (systemArticle.errorCode == 0) {
                Result.success(systemArticle)
            } else {
                Result.failure(RuntimeException("errorCode is ${systemArticle.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<ArticleResponse>(e)
        }
        emit(res)
    }

}