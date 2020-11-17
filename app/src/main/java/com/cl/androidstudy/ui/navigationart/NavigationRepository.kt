package com.cl.androidstudy.ui.navigationart

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.NavigationResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import java.lang.Exception
import java.lang.RuntimeException

object NavigationRepository {
    fun getNavigation() = liveData {
        val res = try {
            val navigationData = ApiNetwork.getNavigation()
            if (navigationData.errorCode == 0) {
                Result.success(navigationData)
            } else {
                Result.failure(RuntimeException("errroCode is ${navigationData.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<NavigationResponse>(e)
        }
        emit(res)
    }
}