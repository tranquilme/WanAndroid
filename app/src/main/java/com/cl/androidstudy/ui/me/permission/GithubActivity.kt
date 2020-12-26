package com.cl.androidstudy.ui.me.permission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseActivity
import com.cl.androidstudy.ui.web.WebActivity
import kotlinx.android.synthetic.main.activity_github.*

class GithubActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)
        iv_github_back.setOnClickListener {
            finish()
        }
        intentToWeb(ll_github_retrofit, "https://github.com/square/retrofit", "Retrofit")
        intentToWeb(ll_github_okhttp, "https://github.com/square/okhttp", "OkHttp")
        intentToWeb(ll_github_yccardview, "https://github.com/yangchong211/YCCardView", "YcCardView")
        intentToWeb(ll_github_circleimageview, "https://github.com/hdodenhof/CircleImageView", "CircleImageView")
    }

    private fun intentToWeb(layout: LinearLayout, link: String, title: String) {
        layout.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java).apply {
                putExtra("link", link)
                putExtra("title", title)
            }
            startActivity(intent)
        }
    }
}