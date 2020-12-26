package com.cl.androidstudy.ui.home.gzh
import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.model.GzhResponse
import com.cl.androidstudy.logic.network.ApiNetwork
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException

object GzhRepository {
    fun getGzhData(id: Int, page: Int) = liveData {

        coroutineScope {
            val res = try {
                val gzhCategoryResponse = async {
                    ApiNetwork.getGzhData()
                }
                val gzhArticleResponse = async {
                    ApiNetwork.getGzhArticle(id, page)
                }
                val gzhCategory = gzhCategoryResponse.await()
                val gzhArticle = gzhArticleResponse.await()
                if (gzhCategory.errorCode == 0 && gzhArticle.errorCode == 0) {
                    val gzhResponse = GzhResponse(gzhCategory, gzhArticle)
                    Result.success(gzhResponse)
                } else {
                    Result.failure(RuntimeException("gzhCategory errorCode is ${gzhCategory.errorCode}, gzhArticle errorCode is ${gzhArticle.errorCode}"))
                }
            }catch (e: Exception) {
                Result.failure<GzhResponse>(e)
            }
            emit(res)
        }
    }
}