package com.cl.androidstudy.logic.network

import android.util.Log
import com.cl.androidstudy.logic.model.ArticleResponse
import java.lang.Exception


object ApiNetwork {
    private val apiService = ServiceCreator.create<ApiService>()   // 创建动态代理对象

    suspend fun getArticle(page: Int) = apiService.getHotArticle(page) // 获取热门文章

    suspend fun getCategory() = apiService.getCategory()    // 获取项目种类

    suspend fun getProject(page: Int, cid: Int) = apiService.getProject(page, cid) // 获取项目内容

    suspend fun getGzhData() = apiService.getGzhData() // 获取公众号数据

    suspend fun getGzhArticle(id: Int, page: Int) = apiService.getGzhArticle(id, page) // 获取公众号文章

    suspend fun getSquare(page: Int) = apiService.getSquare(page) // 获取广场数据

    suspend fun searchArticle(page: Int, keywords: String) = apiService.search(page, keywords) // 搜索文章

    suspend fun getNavigation() = apiService.getNavigation() // 获取导航数据
}