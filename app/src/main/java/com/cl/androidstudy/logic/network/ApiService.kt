package com.cl.androidstudy.logic.network

import com.cl.androidstudy.logic.model.*
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

    @GET("tree/json")
    suspend fun getSystemKind(): SystemKind

    @GET("article/list/{page}/json")
    suspend fun getSystemArticle(@Path("page")page: Int, @Query("cid")cid: Int): ArticleResponse

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username")username: String, @Field("password")password: String): LoginResponse

    @GET("lg/collect/list/{page}/json")
    suspend fun getCollection(@Path("page")page: Int): CollectionResponse

    @GET("lg/coin/userinfo/json")
    suspend fun getIntegralAll(): IntegralAll

    @GET("lg/coin/list/{page}/json")
    suspend fun getIntegralDetail(@Path("page")page: Int): IntegralDetail

    @GET("coin/rank/1/json")
    suspend fun getIntegralRank(): IntegralRankResponse

    @POST("lg/collect/{id}/json")
    suspend fun setCollection(@Path("id")id: Int)

    @POST("lg/uncollect_originId/{id}/json")
    suspend fun setUnollection(@Path("id")id: Int)
}