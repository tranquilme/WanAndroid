package com.cl.androidstudy.ui.system

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cl.androidstudy.common.articleitem.ItemViewModel
import com.cl.androidstudy.logic.model.SystemParams

class SystemViewModel: ItemViewModel(){
    private val systemKindParams = MutableLiveData<Int>()
    private val systemArticleParams = MutableLiveData<SystemParams>()
    var flag = 0 // 0 表示下滑刷新，1表示上拉刷新
    var cid = 0 // 读取的文章列表id

    fun getSystemKind() {
        systemKindParams.value = systemKindParams.value
    }

    fun getSystemArticle(page: Int, cid: Int){
        systemArticleParams.value = SystemParams(page,  cid)
    }

    val systemKindLiveData = Transformations.switchMap(systemKindParams) {
        SystemRepository.getSystemKind()
    }

    val systemArticleLiveData = Transformations.switchMap(systemArticleParams) {
        SystemRepository.getSystemArticle(it.page, it.cid)
    }
}