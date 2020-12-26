package com.cl.androidstudy.ui.home.gzh

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cl.androidstudy.common.articleitem.ItemViewModel
import com.cl.androidstudy.logic.model.GzhParam

class GzhViewModel: ItemViewModel(){
    val paramLiveData = MutableLiveData<GzhParam>() // 监听项目数据
    var flag = MutableLiveData<Int>() // 用于监听categoryItem
    var cid = 408 // 存储与View有关的数据

    fun queryData(page: Int, id: Int) {
        paramLiveData.value = GzhParam(page, id)
    }

    val gzhLiveData = Transformations.switchMap(paramLiveData) {
            param ->
            GzhRepository.getGzhData(param.id, param.page)
    }
}