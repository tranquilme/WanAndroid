package com.cl.androidstudy.logic.model

data class NavigationResponse (val data: List<NavigationData>, val errorCode: Int) {
    data class NavigationData(val articles: List<Articles>, val cid: Int, val name: String)

    data class Articles(val title: String, val link: String)
}