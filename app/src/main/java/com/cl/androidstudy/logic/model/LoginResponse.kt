package com.cl.androidstudy.logic.model

data class LoginResponse (val data: LoginData?, val errorCode: Int) {
    data class LoginData(val id: Int, val nickname: String)
}

data class LoginParams(val username: String, val password: String)