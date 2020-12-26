package com.cl.androidstudy.ui.home.project

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_home_project.*

class ProjectFragment : BaseFragment() {
    private val projectViewModel by lazy { ViewModelProvider(this).get(ProjectViewModel::class.java) }
    private val categoryData = ArrayList<CategoryResponse.Result>()
    private var loadMoreFlag = 0 // 上拉刷新布局的状态
    private var page = 2 // 表示文章页数
    private var flag = 0 // 0 表示下滑刷新，1表示上拉刷新

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_project, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categoryAdapter = CategoryAdapter(categoryData, projectViewModel, 0)
        rv_project_category.adapter = categoryAdapter
        rv_project_category.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        val projectAdapter=
            ArticleAdapter(
                datas,
                this.requireActivity(),
                0,
                projectViewModel
            )
        loadMoreAdapter =
            LoadMoreWrapper(projectAdapter)
        loadMore(loadMoreAdapter)
        rv_project_article.adapter = loadMoreAdapter
        val layoutManager = LinearLayoutManager(activity)
        rv_project_article.layoutManager = layoutManager

        swipeRefreshLayout_project.setColorSchemeResources(R.color.appColorPrimary)
        swipeRefreshLayout_project.isRefreshing = true

        projectViewModel.queryData(1, 294)
        projectViewModel.projectLiveData.observe(viewLifecycleOwner, Observer {
            val res = it.getOrNull()
            if (res != null) {
                val project = res.projectArticle
                val category = res.categoryResponse
                if (flag == 0) { // 下拉刷新，或者是categoryItem点击刷新
                    if (categoryData.isEmpty()) {
                        categoryData.clear()
                        categoryData.addAll(category.data)
                        categoryAdapter.notifyDataSetChanged()
                    }
                    datas.clear()
                    datas.addAll(project.data.datas)
                    loadMoreAdapter.notifyDataSetChanged()
                    swipeRefreshLayout_project.isRefreshing = false
                    layoutManager.scrollToPositionWithOffset(0, 0) // 将item移动到第一条
                    tvAnimation(tv_project_update_in, tv_project_update_on, tv_project_update_out)   // 执行 "内容已更新" 动画
                } else { // 上滑刷新
                    datas.addAll(project.data.datas)
                    loadMoreAdapter.setFootState(3) // 加载结束
                    if (project.data.datas.isEmpty()) {
                        loadMoreFlag = 1
                        loadMoreAdapter.setFootState(4) // 没有更多内容了
                    }
                    page ++
                }
                flag = 1
            }
        })

        projectViewModel.flag.observe(viewLifecycleOwner, Observer {// 监听categoryItem的变化
            if (it == 1) {
                swipeRefreshLayout_project.isRefreshing = true
                flag = 0 // 重新初始化参数
                page = 2
                loadMoreFlag = 0
            }
        })
        swipeRefreshLayout_project.setOnRefreshListener {
            projectViewModel.queryData(1, projectViewModel.cid)
            flag = 0
        }
    }

    private fun loadMore(adapter: LoadMoreWrapper) {
        rv_project_article.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0) {
                    adapter.setFootState(2) // 正在加载中
                    projectViewModel.queryData(page, projectViewModel.cid)
                }
            }
        })
    }
}