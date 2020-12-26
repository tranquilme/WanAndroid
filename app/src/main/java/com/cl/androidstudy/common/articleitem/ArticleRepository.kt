package com.cl.androidstudy.common.articleitem

import androidx.lifecycle.liveData
import com.cl.androidstudy.logic.network.ApiNetwork

object ArticleRepository {
    suspend fun setCollection(id: Int) = ApiNetwork.setCollection(id)

    suspend fun setUncollection(id: Int) = ApiNetwork.setUncollection(id)
}