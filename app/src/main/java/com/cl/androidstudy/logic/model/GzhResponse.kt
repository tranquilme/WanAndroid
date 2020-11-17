package com.cl.androidstudy.logic.model

data class GzhResponse(val categoryResponse: CategoryResponse, val gzhArticle: ArticleResponse)

data class GzhParam(val page: Int, val id: Int)