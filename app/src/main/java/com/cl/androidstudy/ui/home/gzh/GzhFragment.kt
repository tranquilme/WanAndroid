package com.cl.androidstudy.ui.home.gzh

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseFragment
import com.cl.androidstudy.common.loadmore.EndRecyclerOnScrollListener
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.CategoryResponse
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.common.articleitem.ArticleAdapter
import com.cl.androidstudy.ui.home.CategoryAdapter
import kotlinx.android.synthetic.main.fragment_home_gzh.*


class GzhFragment : BaseFragment() {
    private val gzhViewModel by lazy { ViewModelProvider(this).get(GzhViewModel::class.java) }
    private val categoryData = ArrayList<CategoryResponse.Result>()
    private var loadMoreFlag = 0 // 上拉刷新布局的状态
    private var page = 2 // 表示文章页数
    private var flag = 0 // 0 表示下滑刷新，1表示上拉刷新

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_gzh, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categoryAdapter = CategoryAdapter(categoryData, gzhViewModel, 1)
        rv_gzh_category.adapter = categoryAdapter
        rv_gzh_category.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        val gzhAdapter= ArticleAdapter(
            datas,
            this.requireActivity(),
            0,
            gzhViewModel
        )
        loadMoreAdapter =
            LoadMoreWrapper(gzhAdapter)
        loadMore(loadMoreAdapter)
        rv_gzh_article.adapter = loadMoreAdapter
        val layoutManager = LinearLayoutManager(activity)
        rv_gzh_article.layoutManager = layoutManager

        swipeRefreshLayout_gzh.setColorSchemeResources(R.color.appColorPrimary)
        swipeRefreshLayout_gzh.isRefreshing = true
        gzhViewModel.queryData(1, 408)
        gzhViewModel.gzhLiveData.observe(viewLifecycleOwner, Observer {
            val res = it.getOrNull()
            if (res != null) {
                val gzh = res.gzhArticle
                val category = res.categoryResponse
                if (flag == 0) { // 下拉刷新，或者是categoryItem点击刷新
                    if (categoryData.isEmpty()) {
                        categoryData.clear()
                        categoryData.addAll(category.data)
                        categoryAdapter.notifyDataSetChanged()
                    }
                    datas.clear()
                    datas.addAll(gzh.data.datas)
                    loadMoreAdapter.notifyDataSetChanged()
                    swipeRefreshLayout_gzh.isRefreshing = false
                    layoutManager.scrollToPositionWithOffset(0, 0) // 将item移动到第一条
                    tvAnimation(tv_gzh_update_in, tv_gzh_update_on, tv_gzh_update_out)   // 执行 "内容已更新" 动画
                } else { // 上滑刷新
                    datas.addAll(gzh.data.datas)
                    loadMoreAdapter.setFootState(3) // 加载结束
                    if (gzh.data.datas.isEmpty()) {
                        loadMoreFlag = 1
                        loadMoreAdapter.setFootState(4) // 没有更多内容了
                    }
                    page ++
                }
                flag = 1
            }
        })

        gzhViewModel.flag.observe(viewLifecycleOwner, Observer {// 监听categoryItem的变化
            if (it == 1) {
                swipeRefreshLayout_gzh.isRefreshing = true
                flag = 0 // 重新初始化参数
                page = 2
                loadMoreFlag = 0
            }
        })
        swipeRefreshLayout_gzh.setOnRefreshListener {
            gzhViewModel.queryData(1, gzhViewModel.cid)
            flag = 0
        }
    }

    private fun loadMore(adapter: LoadMoreWrapper) {
        rv_gzh_article.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0) {
                    adapter.setFootState(2) // 正在加载中
                    gzhViewModel.queryData(page, gzhViewModel.cid)
                }
            }
        })
    }

}