package com.cl.androidstudy.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cl.androidstudy.logic.model.SearchParam
import com.cl.androidstudy.ui.home.gzh.GzhRepository
import com.cl.androidstudy.ui.home.searchD.SearchRepository

class SearchViewModel: ViewModel(){
    private val paramLiveData = MutableLiveData<SearchParam>()

    fun queryArticle(page: Int, keywords: String) {
        paramLiveData.value = SearchParam(page, keywords)
    }

    val searchLiveData = Transformations.switchMap(paramLiveData) {
        param ->
        SearchRepository.searchArticle(param.page, param.keywords)
    }
}