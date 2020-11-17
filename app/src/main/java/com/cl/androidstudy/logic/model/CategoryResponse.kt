package com.cl.androidstudy.logic.model

class CategoryResponse(
    val data: List<Result>, val errorCode: Int
) {
    class Result(val name: String, val id: Int)
}