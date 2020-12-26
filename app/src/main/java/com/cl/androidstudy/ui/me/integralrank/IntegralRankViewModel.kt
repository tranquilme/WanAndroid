package com.cl.androidstudy.ui.me.integralrank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class IntegralRankViewModel: ViewModel(){
    val integralParams = MutableLiveData<Int>()

    fun getIntegranRank() {
        integralParams.value = integralParams.value
    }

    val integralRankLiveData = Transformations.switchMap(integralParams) {
        IntegralRankRepository.getIntegralRank()
    }
}