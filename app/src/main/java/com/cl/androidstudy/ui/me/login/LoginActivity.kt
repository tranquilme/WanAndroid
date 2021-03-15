package com.cl.androidstudy.ui.me.login

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.common.LoginState
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT > 23) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 设置decorView的样式
            window.statusBarColor = ContextCompat.getColor(this, R.color.appColorSecondary)
        }
        sr_login_login.setColorSchemeResources(R.color.appColorPrimary)
        button_login.setOnClickListener {
            val username = et_login_username.text.toString()
            val password = et_login_password.text.toString()
            sr_login_login.isRefreshing = true
            loginViewModel.login(username, password)
        }
        loginViewModel.loginLiveData.observe(this, Observer {
            val res = it.getOrNull()
            if (res != null) {
                if (res.errorCode == -1) {
                    val sharedPreferences = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    Toast.makeText(this, "密码错误！", Toast.LENGTH_SHORT).show()
                    sr_login_login.isRefreshing = false
                }else {
                    sr_login_login.isRefreshing = false
                    Toast.makeText(this, "登陆成功!", Toast.LENGTH_SHORT).show()
                    val preference = LoginState.preferences
                    val editor = preference.edit().apply {
                        putString("username", res.data?.nickname)
                        putString("userid", res.data?.id.toString())
                    }
                    editor.apply()
                    finish()
                }
            } else {
                Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show()
            }
        })
    }

}