package com.cl.androidstudy.logic.network

import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.model.CategoryResponse
import com.cl.androidstudy.logic.model.NavigationResponse
import retrofit2.http.*

interface ApiService {
    @GET("article/list/{page}/json")
    suspend fun getHotArticle(@Path("page")page: Int): ArticleResponse

    @GET("project/tree/json")
    suspend fun getCategory(): CategoryResponse

    @GET("project/list/{page}/json")
    suspend fun getProject(@Path("page")page: Int, @Query("cid") cid: Int): ArticleResponse

    @GET("wxarticle/chapters/json")
    suspend fun getGzhData(): CategoryResponse

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getGzhArticle(@Path("id")id: Int, @Path("page")page: Int): ArticleResponse

    @GET("user_article/list/{page}/json")
    suspend fun getSquare(@Path("page")page: Int): ArticleResponse

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(@Path("page")page: Int, @Field("k") keywords:String): ArticleResponse

    @GET("navi/json")
    suspend fun getNavigation(): NavigationResponse
}