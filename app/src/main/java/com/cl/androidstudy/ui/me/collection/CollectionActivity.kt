package com.cl.androidstudy.ui.me.collection

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseActivity
import com.cl.androidstudy.common.loadmore.EndRecyclerOnScrollListener
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.CollectionResponse
import kotlinx.android.synthetic.main.activity_collection.*

class CollectionActivity : BaseActivity() {
    private val TAG = "CollectionActivity.class"
    private val collectionViewModel by lazy { ViewModelProvider(this).get(CollectionViewModel::class.java) }
    private val datas = ArrayList<CollectionResponse.CollectionDatas>()
    private var page = 1
    private var flag = 0 // 0 表示下滑刷新，1表示上拉刷新
    private var loadMoreFlag = 0 // 0表示加载数据 ， 1表示不加载数据，避免已经拉到最低部又多次滑动多次加载数据
    private var dataSize = 20 // <20表示数据只有一页
    private lateinit var loadMoreAdapter: LoadMoreWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
        val cookie = preferences.getStringSet("cookies", null)
        if (cookie == null) {
            toLogin(this)
        }
        setContentView(R.layout.activity_collection)
        iv_collection_back.setOnClickListener {
            finish()
        }
        rv_collection.layoutManager = LinearLayoutManager(this)
        val adapter = CollectionAdapter(datas, this, collectionViewModel, object : RemoveImpl {
            override fun remove(position: Int, itemCount: Int) {
                loadMoreAdapter.notifyItemRemoved(position)
                loadMoreAdapter.notifyItemRangeChanged(position, itemCount)
            }
        })
        loadMoreAdapter = LoadMoreWrapper(adapter)
        loadMore(loadMoreAdapter)
        rv_collection.adapter = loadMoreAdapter
        sr_collection.setColorSchemeResources(R.color.appColorPrimary)
        sr_collection.isRefreshing = true
        collectionViewModel.getCollection(0) // 查询第一页数据
        collectionViewModel.collectionLiveData.observe(this, Observer {
            val res = it.getOrNull()    // 如果出错数据就为空
            Log.d(TAG, "onCreate: $res")
            if (res != null) {
                if (flag == 0) { // 下滑刷新
                    datas.clear()
                    datas.addAll(res.data.datas)
                    dataSize = datas.size
                    loadMoreAdapter.notifyDataSetChanged()
                    sr_collection.isRefreshing = false
                    tvAnimation(tv_collection_update_in, tv_collection_update_on, tv_collection_update_out)   // 执行 "内容已更新" 动画
                    if (dataSize < 10) {
                        loadMoreAdapter.setFootState(4)
                        loadMoreFlag = 1
                    }
                } else if (flag == 1) { // 上拉刷新
                    datas.addAll(res.data.datas)
                    loadMoreAdapter.setFootState(3) // 加载结束
                    page++
                    loadMoreFlag = 0
                    if (res.data.datas.isEmpty()) {
                        loadMoreFlag = 1
                        loadMoreAdapter.setFootState(4)
                    }
                }
                flag = 1
            }

        })
        sr_collection.setOnRefreshListener {
            collectionViewModel.getCollection(0)
            flag = 0 // 设置标记为 0 ，代表下滑刷新
        }
    }

    fun loadMore(adapter: LoadMoreWrapper) {
        rv_collection.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0) {
                    adapter.setFootState(2) // 正在加载中
                    collectionViewModel.getCollection(page)
                    loadMoreFlag = 1
                }
            }
        })
    }

}