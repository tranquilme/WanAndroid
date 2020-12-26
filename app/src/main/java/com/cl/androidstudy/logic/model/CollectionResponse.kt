package com.cl.androidstudy.logic.model

data class CollectionResponse(val data: CollectionData, val errorCode: Int) {
    data class CollectionData(val datas: List<CollectionDatas>)

    data class CollectionDatas(
        val author: String, val desc: String, val link: String,
        val niceDate: String, val title: String, val id: Int,
        val originId: Int
    )
}