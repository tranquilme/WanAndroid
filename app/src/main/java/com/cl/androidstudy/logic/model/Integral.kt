package com.cl.androidstudy.logic.model

data class IntegralAll (val data: IntegralAllData, val errorCode: Int) {
    data class IntegralAllData(val coinCount: Int, val level: Int, val rank: String)
}

data class IntegralDetail(val data: IntegralDetailData, val errorCode: Int) {
    data class IntegralDetailData(val datas: ArrayList<IntegralDetailDatas>)

    data class IntegralDetailDatas(val coinCount: Int, val desc: String, val reason: String)
}

data class Integral(var integralAll: IntegralAll, val integralDetail: IntegralDetail)