package com.cl.androidstudy.logic.model

data class ArticleResponse(val data: Data, val errorCode: Int) {
    data class Data(val datas: List<Datas>)
}

data class Datas(
    val link: String, val niceDate: String, val title: String,
    val shareUser: String, val desc: String, val author: String
)