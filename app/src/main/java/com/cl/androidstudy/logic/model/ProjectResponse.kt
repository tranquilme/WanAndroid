package com.cl.androidstudy.logic.model

data class ProjectParam(val page: Int, val cid: Int)

data class ProjectResponse (val categoryResponse: CategoryResponse, val projectArticle: ArticleResponse)