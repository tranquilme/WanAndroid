package com.cl.androidstudy.ui.me.integralme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MyIntegralViewModel: ViewModel() {
    val integralParams = MutableLiveData<Int>()

    fun getIntegral(page: Int) {
        integralParams.value = page
    }

    val integralLiveData = Transformations.switchMap(integralParams) {
        MyIntegralRepository.getIntegral(it)
    }
}