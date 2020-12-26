package com.cl.androidstudy.ui.me.integralrank

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.IntegralRankResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object IntegralRankRepository {
    fun getIntegralRank() = liveData(Dispatchers.IO) {
        val res = try {
            val rankResponse = ApiNetwork.getIntegralRank()
            if (rankResponse.errorCode == 0) {
                Result.success(rankResponse)
            } else {
                Result.failure(RuntimeException("errorCode is ${rankResponse.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<IntegralRankResponse>(e)
        }
        emit(res)
    }
}