package com.cl.androidstudy.logic.model

data class IntegralRankResponse (val data: RankData, val errorCode: Int) {
    data class RankData(val datas: List<RankDatas>)

    data class RankDatas(val coinCount: Int, val rank: String, val username: String)
}