package com.cl.androidstudy.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cl.androidstudy.ui.me.login.LoginActivity
import kotlin.concurrent.thread

open class BaseActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT > 23) {
            val decoreView = window.decorView
            decoreView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
        super.onCreate(savedInstanceState)
    }

    fun tvAnimation(tv_update_in: TextView, tv_update_on: TextView, tv_update_out: TextView) {  // tv 淡入动画
        tv_update_in.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_update_in.visibility = View.GONE
                        tvOnAnimation(tv_update_on, tv_update_out)
                    }
                })
        }
    }

    fun tvOnAnimation(tv_update_on: TextView, tv_update_out: TextView) {   // tv持续动画
        tv_update_on.visibility = View.VISIBLE
        tv_update_on.apply {
            alpha = 1f
            animate().alpha(1f)
                .setDuration(800)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_update_on.visibility = View.GONE
                        tvOutAnimation(tv_update_out)
                    }
                })
        }
    }

    fun tvOutAnimation(tv_update_out: TextView) {  // tv淡出动画
        tv_update_out.visibility = View.VISIBLE
        tv_update_out.apply {
            alpha = 1f
            animate().alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_update_out.visibility = View.GONE
                    }
                })
        }
    }

    fun toLogin(context: Context) { // 如果未登录，需要先登录才能查看积分以及收藏
        Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}