package com.cl.androidstudy.ui.home.square

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseFragment
import com.cl.androidstudy.common.loadmore.EndRecyclerOnScrollListener
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.common.articleitem.ArticleAdapter
import kotlinx.android.synthetic.main.fragment_home_square.*
import kotlinx.android.synthetic.main.fragment_home_square.square_swipeRefreshLayout

class SquareFragment : BaseFragment() {
    private val squareViewModel by lazy { ViewModelProvider(this).get(SquareViewModel::class.java) }
    private var page = 1
    private var flag = 0 // 0 表示下滑刷新，1表示上拉刷新
    private var loadMoreFlag = 0 // 0表示加载数据 ， 1表示不加载数据，避免已经拉到最低部又多次滑动多次加载数据

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_square, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_square_article.layoutManager = LinearLayoutManager(activity)
        val adapter = ArticleAdapter(
            datas,
            this.requireActivity(),
            0,
            squareViewModel
        )
        loadMoreAdapter =
            LoadMoreWrapper(adapter)
        loadMore(loadMoreAdapter)
        rv_square_article.adapter = loadMoreAdapter
        square_swipeRefreshLayout.setColorSchemeResources(R.color.appColorPrimary)
        square_swipeRefreshLayout.isRefreshing = true
        squareViewModel.queryArticle(0) // 查询第一页数据
        squareViewModel.articleLiveData.observe(viewLifecycleOwner, Observer {
            val res = it.getOrNull()    // 如果出错数据就为空
            if (res != null) {
                if (flag == 0) { // 下滑刷新
                    datas.clear()
                    datas.addAll(res.data.datas)
                    loadMoreAdapter.notifyDataSetChanged()
                    square_swipeRefreshLayout.isRefreshing = false
                    tvAnimation(tv_square_update_in, tv_square_update_on, tv_square_update_out)   // 执行 "内容已更新" 动画
                }else if (flag == 1) { // 上拉刷新
                    datas.addAll(res.data.datas)
                    loadMoreAdapter.setFootState(3) // 加载结束
                    page ++
                    loadMoreFlag = 0
                }
                flag = 1
            }
        })
        square_swipeRefreshLayout.setOnRefreshListener {
            squareViewModel.queryArticle(0)
            flag = 0 // 设置标记为 0 ，代表下滑刷新
        }
    }

    fun loadMore(adapter: LoadMoreWrapper) {
        rv_square_article.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0) {
                    adapter.setFootState(2) // 正在加载中
                    squareViewModel.queryArticle(page)
                    loadMoreFlag = 1
                }
            }
        })
    }

}