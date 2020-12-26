package com.cl.navicationtest

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.MainActivity
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseFragment
import com.cl.androidstudy.common.articleitem.ArticleAdapter
import com.cl.androidstudy.common.loadmore.EndRecyclerOnScrollListener
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.logic.model.SystemKind
import com.cl.androidstudy.ui.system.BottomDialogAdapter
import com.cl.androidstudy.ui.system.SystemViewModel
import kotlinx.android.synthetic.main.dialog_system.*
import kotlinx.android.synthetic.main.dialog_system.imageView
import kotlinx.android.synthetic.main.fragment_system.*


class SystemFragment : BaseFragment() {
    private val systemViewModel by lazy { ViewModelProvider(this).get(SystemViewModel::class.java) }
    private val systemData = ArrayList<SystemKind.SystemKindData>()
    private var loadMoreFlag = 0 // 0表示加载数据 ， 1表示不加载数据，避免已经拉到最低部又多次滑动多次加载数据
    private var page = 1
    lateinit var bottomDialog: Dialog
    lateinit var adapter: BottomDialogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeDialog() // 初始化底部对话框
        systemViewModel.getSystemKind()
        sr_system_article.isRefreshing = true
        sr_system_article.setColorSchemeResources(R.color.appColorPrimary)
        val layoutManager = LinearLayoutManager(requireContext())
        rv_system_article.layoutManager = layoutManager
        val articleAdapter =
            ArticleAdapter(
                datas,
                requireContext(),
                0,
                systemViewModel
            )
        loadMoreAdapter =
            LoadMoreWrapper(articleAdapter)
        loadMore(loadMoreAdapter)
        rv_system_article.adapter = loadMoreAdapter
        systemViewModel.systemKindLiveData.observe(viewLifecycleOwner, Observer { // 监听体系类型
            val res = it.getOrNull()
            if (res != null) {
                systemData.addAll(res.data)
                systemViewModel.cid = res.data[0].children[0].id
                systemViewModel.getSystemArticle(0, systemViewModel.cid)
            }
        })
        systemViewModel.systemArticleLiveData.observe(viewLifecycleOwner, Observer {// 监听具体文章数据
            val res = it.getOrNull()
            if (res != null) {
                val data = res.data.datas
                if (data.isNotEmpty()) {
                    systemViewModel.cid = data[0].chapterId    // 更改cid 用于跳转到其他文章列表时能够正确刷新
                }
                if (systemViewModel.flag == 0) { // 下滑刷新
                    layoutManager.scrollToPosition(0) // 让item回到第一个位置，否则会保持原来的状态
                    datas.clear()
                    datas.addAll(data)
                    loadMoreAdapter.setFootState(3) // 当数据未填满第一页时，默认加载完成。
                    sr_system_article.isRefreshing = false
                    // 如果无文章要做特殊处理
                    tv_system_tagname.text = if (data.isNotEmpty()) data[0].chapterName else "无相关文章"
                    tv_system_title.text = if (data.isNotEmpty()) data[0].superChapterName else ""
                    iv_system_in.visibility = View.VISIBLE
                    tv_system_title.visibility = if (data.isNotEmpty()) View.VISIBLE else View.GONE
                    tvAnimation(tv_system_update_in, tv_system_update_on, tv_system_update_out)   // 执行 "内容已更新" 动画
                } else {
                    datas.addAll(res.data.datas)
                    loadMoreAdapter.setFootState(3) // 加载结束
                    if (res.data.datas.isEmpty()) {
                        loadMoreFlag = 1
                        loadMoreAdapter.setFootState(4) // 没有更多内容了
                    }
                    page++
                    sr_system_article.isRefreshing = false
                }
                systemViewModel.flag = 1
            }
        })
        iv_system_in.setOnClickListener {
            bottomDialog.show()     // 显示底部对话框
            adapter.notifyDataSetChanged()
        }
        sr_system_article.setOnRefreshListener {
            systemViewModel.getSystemArticle(0, systemViewModel.cid)
            systemViewModel.flag = 0
            loadMoreFlag = 0
            page = 1    // 重新设置参数
        }
        rv_system_article.addOnScrollListener(object : RecyclerView.OnScrollListener() { // 监听recyclerview，显示或者隐藏底部导航栏
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val activity = activity as MainActivity
                if (dy > 10) {
                    activity.bottomAnimation(false)
                }
                if (dy < -10) {
                    activity.bottomAnimation(true)
                }
            }
        })
    }

    private fun initializeDialog() {
        bottomDialog = Dialog(requireActivity(), R.style.BottomDialog)  // 构造 Dialog 对象
        // 将layout文件转化为View，此View是最外层View，root为null时View的layoutParams对象也为null，root不为null时，会为每个子View构造layoutParms对象
        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_system, null)
        val layoutParams = ViewGroup.LayoutParams(  // 构造layoutParms对象
            resources.displayMetrics.widthPixels,
            (resources.displayMetrics.heightPixels * 0.88).toInt()
        )
        bottomDialog.setContentView(view, layoutParams) // 为Dialog设置布局，同时传入自定义的layoutParams参数
        bottomDialog.imageView.setOnClickListener { // 为dialog设置退出动画
            val animation =
                AnimationUtils.loadAnimation(requireActivity(), R.anim.translate_dialog_out)
            view.startAnimation(animation)
            bottomDialog.cancel()   // 取消Dialog，否则会一直存在
        }
        bottomDialog.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = BottomDialogAdapter(systemData, requireActivity(), systemViewModel, this)
        bottomDialog.recyclerview.adapter = adapter
        bottomDialog.setCancelable(true)    // 设置Dialog可以点击外部取消
        bottomDialog.window?.setGravity(Gravity.BOTTOM) // 设置Dialog位置为底部
        bottomDialog.window?.setWindowAnimations(R.style.BottomDialog_Animation)    // 为Dialog进入退出设置动画
    }

    fun loadMore(adapter: LoadMoreWrapper) {
        rv_system_article.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0) {
                    adapter.setFootState(2) // 正在加载中
                    systemViewModel.getSystemArticle(page, systemViewModel.cid)
                }
            }
        })
    }
}