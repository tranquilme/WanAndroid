package com.cl.androidstudy.ui.home.square

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import java.lang.Exception
import java.lang.RuntimeException

object SquareRepository {
    fun getSquare(page: Int) = liveData {
        val res = try {
            val squareData = ApiNetwork.getSquare(page)
            if (squareData.errorCode == 0) {
                Result.success(squareData)
            }else {
                Result.failure(RuntimeException("errorCode is ${squareData.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<ArticleResponse>(e)
        }
        emit(res)
    }
}