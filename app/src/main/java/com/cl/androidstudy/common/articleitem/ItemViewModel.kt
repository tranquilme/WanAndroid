package com.cl.androidstudy.common.articleitem

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class ItemViewModel: ViewModel(){
    fun setCollection(id: Int) {
        viewModelScope.launch {
            ArticleRepository.setCollection(id)
        }
    }

    fun setUncollection(id: Int) {
        viewModelScope.launch {
            ArticleRepository.setUncollection(id)
        }
    }
}