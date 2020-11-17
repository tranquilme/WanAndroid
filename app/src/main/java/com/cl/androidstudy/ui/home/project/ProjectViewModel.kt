package com.cl.androidstudy.ui.home.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cl.androidstudy.logic.model.ProjectParam

class ProjectViewModel: ViewModel() {
    val paramLiveData = MutableLiveData<ProjectParam>() // 监听项目数据
    var flag = MutableLiveData<Int>() // 用于监听categoryItem
    var cid = 294 // 存储与View有关的数据

    fun queryData(page: Int, cid: Int) {
        paramLiveData.value = ProjectParam(page, cid)
    }

    val projectLiveData = Transformations.switchMap(paramLiveData) {
        param ->
        ProjectRepository.getProjectData(param.page, param.cid)
    }
}