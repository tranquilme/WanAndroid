package com.cl.androidstudy.ui.home.square

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SquareViewModel: ViewModel() {
    private var pageLiveData = MutableLiveData<Int>()

    fun queryArticle(page: Int) {
        pageLiveData.value = page
    }

    // 当 pageLiveData 改变时触发 switchMap() 函数，返回一个liveData
    val articleLiveData = Transformations.switchMap(pageLiveData) {
            page ->
        SquareRepository.getSquare(page)
    }
}