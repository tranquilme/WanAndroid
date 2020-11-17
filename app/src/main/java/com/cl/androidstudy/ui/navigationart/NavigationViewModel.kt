package com.cl.androidstudy.ui.navigationart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class NavigationViewModel: ViewModel(){
    private val searchLiveData = MutableLiveData<Int>()

    fun update() {
        searchLiveData.value = searchLiveData.value  // 只要是赋值语句 searchLiveData 就会被监听到
    }

    val navigationLiveData = Transformations.switchMap(searchLiveData) {
        NavigationRepository.getNavigation()
    }
}