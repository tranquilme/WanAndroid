package com.cl.androidstudy.ui.me.collection

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.CollectionResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import java.lang.Exception
import java.lang.RuntimeException

object CollectionRepository {
    fun getCollection(page: Int) = liveData {
        val res = try {
            val collectionResponse = ApiNetwork.getCollection(page)
            if (collectionResponse.errorCode == 0) {
                Result.success(collectionResponse)
            } else {
                Result.failure(RuntimeException("errorCode is ${collectionResponse.errorCode}"))
            }
        } catch (e: Exception) {
            Result.failure<CollectionResponse>(e)
        }
        emit(res)
    }
}