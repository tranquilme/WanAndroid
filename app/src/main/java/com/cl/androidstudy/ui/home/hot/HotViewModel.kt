package com.cl.androidstudy.ui.home.hot

import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.*
import com.cl.androidstudy.common.articleitem.ItemViewModel
import com.cl.androidstudy.logic.model.ArticleResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class HotViewModel : ItemViewModel() {
    private var pageLiveData = MutableLiveData<Int>()

    fun queryArticle(page: Int) {
        pageLiveData.value = page
    }

    // 当 pageLiveData 改变时触发 switchMap() 函数，返回一个liveData
    val articleLiveData = Transformations.switchMap(pageLiveData) {
        page ->
        HotRepository.getArticle(page)
    }
}