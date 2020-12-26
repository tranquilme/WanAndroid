package com.cl.androidstudy.ui.me.share

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseActivity
import kotlinx.android.synthetic.main.activity_share.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 还未分享过文章，无法得知API json格式
 */
class ShareActivity : BaseActivity() {
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        iv_share_back.setOnClickListener {
            finish()
        }
        val preferences = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
        val cookie = preferences.getStringSet("cookies", null)
        if (cookie == null) {
            toLogin(this)
        }
        sr_share.setColorSchemeResources(R.color.appColorPrimary)
        sr_share.isRefreshing = true
        val scope = CoroutineScope(job)
        scope.launch {
            Log.d("tagg", "s1" + Thread.currentThread().toString())
            delay(2000)
            runOnUiThread {
                tvAnimation(tv_share_update_in, tv_share_update_on, tv_share_update_out)
                sr_share.isRefreshing = false
                tv_share_content.visibility = View.VISIBLE
            }
        }

        sr_share.setOnRefreshListener {
            scope.launch {
                Log.d("tagg", "s2" + Thread.currentThread().toString())
                delay(2000)
                runOnUiThread {
                    tvAnimation(tv_share_update_in, tv_share_update_on, tv_share_update_out)
                    sr_share.isRefreshing = false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}