package com.cl.androidstudy.logic.model

data class SystemKind (val data: List<SystemKindData>, val errorCode: Int){
    data class SystemKindData(val children: List<Children>, val name: String)

    data class Children(val id: Int, val name: String)
}

data class SystemParams(val page: Int, val cid: Int)