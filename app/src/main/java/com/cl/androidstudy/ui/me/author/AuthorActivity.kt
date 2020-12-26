package com.cl.androidstudy.ui.me.author

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseActivity
import com.cl.androidstudy.ui.web.WebActivity
import kotlinx.android.synthetic.main.activity_author.*
import kotlinx.android.synthetic.main.activity_github.*


class AuthorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)
        iv_author_back.setOnClickListener {
            finish()
        }
        intentToWeb(ll_author_csdn, "https://blog.csdn.net/weixin_47885879?spm=1010.2135.3001.5113", "CSDN")
        intentToWeb(ll_author_github, "https://github.com/tranquilme", "Github")
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