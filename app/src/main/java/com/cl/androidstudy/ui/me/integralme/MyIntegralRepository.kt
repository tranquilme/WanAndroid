package com.cl.androidstudy.ui.me.integralme

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.Integral
import com.cl.androidstudy.logic.network.ApiNetwork
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

object MyIntegralRepository {
    fun getIntegral(page: Int) = liveData {
        coroutineScope {
            val res = try {
                val integralAllData = async {
                    ApiNetwork.getIntegralAll()
                }
                val integralDetailData = async {
                    ApiNetwork.getIntegralDetail(page)
                }
                val integralAll = integralAllData.await()
                val integralDetail = integralDetailData.await()
                if (integralAll.errorCode == 0 && integralDetail.errorCode == 0) {
                    val integral = Integral(integralAll, integralDetail)
                    Result.success(integral)
                } else {
                    Result.failure(RuntimeException("integralAll errorCode is ${integralAll.errorCode},integralDetail errorCode is ${integralDetail.errorCode}"))
                }
            } catch (e: Exception) {
                Result.failure<Integral>(e)
            }
            emit(res)
        }

    }
}