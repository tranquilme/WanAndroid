package com.cl.androidstudy.logic.network



object ApiNetwork {
    private val apiService = ServiceCreator.create<ApiService>()   // 创建动态代理对象

    private val loginService = ServiceCreator.createLogin<ApiService>()

    private val cookieService = ServiceCreator.createCookie<ApiService>()

    suspend fun getArticle(page: Int) = cookieService.getHotArticle(page) // 获取热门文章

    suspend fun getCategory() = apiService.getCategory()    // 获取项目种类

    suspend fun getProject(page: Int, cid: Int) = apiService.getProject(page, cid) // 获取项目内容

    suspend fun getGzhData() = apiService.getGzhData() // 获取公众号数据

    suspend fun getGzhArticle(id: Int, page: Int) = apiService.getGzhArticle(id, page) // 获取公众号文章

    suspend fun getSquare(page: Int) = apiService.getSquare(page) // 获取广场数据

    suspend fun searchArticle(page: Int, keywords: String) = apiService.search(page, keywords) // 搜索文章

    suspend fun getNavigation() = apiService.getNavigation() // 获取导航数据

    suspend fun getSystemKind() = apiService.getSystemKind() // 获取体系类别

    suspend fun getSystemArticle(page: Int, cid: Int) = apiService.getSystemArticle(page,cid) // 获取体系文章

    suspend fun login(username: String, password: String) = loginService.login(username, password) // 登录

    suspend fun getCollection(page: Int) = cookieService.getCollection(page) // 获取收藏文章

    suspend fun getIntegralAll() = cookieService.getIntegralAll()   // 获取总积分

    suspend fun getIntegralDetail(page: Int) = cookieService.getIntegralDetail(page) // 获取积分细节

    suspend fun getIntegralRank() = apiService.getIntegralRank() // 获取积分排行榜数据

    suspend fun setCollection(id: Int) = cookieService.setCollection(id) // 添加收藏文章

    suspend fun setUncollection(id: Int) = cookieService.setUnollection(id) // 取消收藏文章
}