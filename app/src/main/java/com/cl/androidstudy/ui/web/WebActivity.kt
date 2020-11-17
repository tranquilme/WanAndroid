package com.cl.androidstudy.ui.web

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import com.cl.androidstudy.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    private lateinit var webView: WebView // 防止内存泄漏，不直接在xml中定义webView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        if (Build.VERSION.SDK_INT > 23) { // 状态栏黑色字体
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
        webView = WebView(applicationContext)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) { // Android 5.0 默认不允许混合模式，网页是https，图片引用是http
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        val link = intent.getStringExtra("link")
        val title = intent.getStringExtra("title")
        tv_title.text = title

        frameLayout.addView(webView)
        webView.loadUrl(link)

        val settings = webView.settings // 对WebView进行配置和管理
        settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() { // 处理各种通知 & 请求事件
            override fun shouldOverrideUrlLoading( // 在WebView中处理网页中的其他链接Url
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                if (url.startsWith("jianshu:")) {
                    return true // 自己处理url
                }
                return false  // 交给WebView处理url,即用WebView加载其他链接url
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d("tagg", "arr finish")
            }

        }

        webView.webChromeClient = object : WebChromeClient() { //辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onProgressChanged(view: WebView?, newProgress: Int) { // 设置进度条
                progressBar2.setProgress(newProgress, true)
                if (newProgress == 100){
                    progressBar2.animate().setDuration(900).alpha(0f).setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            progressBar2.visibility = View.GONE
                        }
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        webView.visibility = View.GONE
        webView.clearHistory()
        webView.destroy() // 销毁 weiView
        super.onDestroy()
    }
}
