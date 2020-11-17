package com.cl.androidstudy.ui.home.search

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.androidstudy.R
import com.cl.androidstudy.common.loadmore.EndRecyclerOnScrollListener
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.ui.home.ArticleAdapter
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity() {
    private val searchViewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }
    private val searchData = ArrayList<Datas>()
    private var loadMoreFlag = 0 // 上拉刷新布局的状态
    private var page = 2 // 表示文章页数
    private var flag = 0 // 0 表示下滑刷新，1表示上拉刷新
    private lateinit var keywords: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if (Build.VERSION.SDK_INT > 23) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 设置decorView的样式
            window.statusBarColor = Color.WHITE // 状态栏颜色
        }
        keywords = intent.getStringExtra("keywords")!!
        val searchAdapter= ArticleAdapter(searchData, this, 1)
        val loadMoreAdapter =
            LoadMoreWrapper(searchAdapter)
        loadMore(loadMoreAdapter)
        rv_search.adapter = loadMoreAdapter
        val layoutManager = LinearLayoutManager(this)
        rv_search.layoutManager = layoutManager

        swipeRefreshLayout_search.setColorSchemeResources(R.color.appColorPrimary)
        swipeRefreshLayout_search.isRefreshing = true
        searchViewModel.queryArticle(0, keywords)
        searchViewModel.searchLiveData.observe(this, Observer {
            val res = it.getOrNull()
            if (res != null) {
                if (flag == 0) { // 下拉刷新，或者是categoryItem点击刷新
                    searchData.clear()
                    searchData.addAll(res)
                    loadMoreAdapter.notifyDataSetChanged()
                    swipeRefreshLayout_search.isRefreshing = false
                    tvAnimation()   // 执行 "内容已更新" 动画
                } else { // 上滑刷新
                    searchData.addAll(res)
                    loadMoreAdapter.setFootState(3) // 加载结束
                    if (res.isEmpty()) {
                        loadMoreFlag = 1
                        loadMoreAdapter.setFootState(4) // 没有更多内容了
                    }
                    page ++
                }
                flag = 1
            }
        })

        swipeRefreshLayout_search.setOnRefreshListener {
            searchViewModel.queryArticle(0, keywords)
            flag = 0
        }
    }

    private fun loadMore(adapter: LoadMoreWrapper) {
        rv_search.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0) {
                    adapter.setFootState(2) // 正在加载中
                    searchViewModel.queryArticle(page, keywords)
                }
            }
        })
    }

    fun tvAnimation() {  // tv 淡入动画
        tv_search_update_in.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_search_update_in.visibility = View.GONE
                        tvOnAnimation()
                    }
                })
        }
    }

    fun tvOnAnimation() {   // tv持续动画
        tv_search_update_on.visibility = View.VISIBLE
        tv_search_update_on.apply {
            alpha = 1f
            animate().alpha(1f)
                .setDuration(800)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_search_update_on.visibility = View.GONE
                        tvOutAnimation()
                    }
                })
        }
    }

    fun tvOutAnimation() {  // tv淡出动画
        tv_search_update_out.visibility = View.VISIBLE
        tv_search_update_out.apply {
            alpha = 1f
            animate().alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_search_update_out.visibility = View.GONE
                    }
                })
        }
    }
}