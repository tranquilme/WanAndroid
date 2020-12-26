package com.cl.androidstudy.ui.me.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cl.androidstudy.logic.model.LoginParams

class LoginViewModel: ViewModel(){
    val loginParams = MutableLiveData<LoginParams>()

    fun login(username: String, password: String) {
        loginParams.value = LoginParams(username, password)
    }

    val loginLiveData = Transformations.switchMap(loginParams) {
        LoginRepository.login(it.username, it.password)
    }
}