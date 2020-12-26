package com.cl.androidstudy.ui.me.quit

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.ui.me.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_me.*
import java.lang.IllegalStateException

class QuitDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("是否确定退出？")
            builder.setPositiveButton("是") { _, _ ->
                it.iv_login.setImageResource(R.color.appColorPrimary)
                it.tv_me_username.text = "点击图片登录"
                it.tv_me_userid.text = "ID:00000"
                val sharedPreferences = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("否", null)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}