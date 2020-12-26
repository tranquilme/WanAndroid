package com.cl.androidstudy.ui.me.collection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cl.androidstudy.common.articleitem.ItemViewModel

class CollectionViewModel: ItemViewModel(){
    val collectionParams = MutableLiveData<Int>()

    fun getCollection(page: Int) {
        collectionParams.value = page
    }

    val collectionLiveData = Transformations.switchMap(collectionParams) {
        CollectionRepository.getCollection(it)
    }
}