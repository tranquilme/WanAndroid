package com.cl.androidstudy.ui.me.integralme

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseActivity
import com.cl.androidstudy.common.loadmore.EndRecyclerOnScrollListener
import com.cl.androidstudy.logic.model.IntegralAll
import com.cl.androidstudy.logic.model.IntegralDetail
import com.cl.androidstudy.ui.me.login.LoginActivity
import kotlinx.android.synthetic.main.activity_my_integral.*

class MyIntegralActivity : BaseActivity() {
    private val myIntegralViewModel by lazy { ViewModelProvider(this).get(MyIntegralViewModel::class.java) }
    private var page = 2
    private val integralDetail = ArrayList<IntegralDetail.IntegralDetailDatas>()
    private val integralAll = ArrayList<IntegralAll>()
    private var flag = 0
    private var loading = 0 // 0表示为加载，1表示正在加载中，无法继续执行上拉操作
    private var loadMoreFlag = 0 // 上拉刷新布局的状态

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = MyApplication.context.getSharedPreferences("cookieData", Context.MODE_PRIVATE)
        val cookie = preferences.getStringSet("cookies", null)
        if (cookie == null) {
            toLogin(this)
        }
        setContentView(R.layout.activity_my_integral)
        rv_myintegral.layoutManager = LinearLayoutManager(this)
        val adapter = IntegralAdapter(integralAll, integralDetail)
        rv_myintegral.adapter = adapter  // 为adapter添加观察者
        loadMore(adapter)
        myIntegralViewModel.getIntegral(0)
        sr_integralme.isRefreshing = true
        sr_integralme.setColorSchemeResources(R.color.appColorPrimary)
        iv_integralme_back.setOnClickListener { 
            finish()
        }
        myIntegralViewModel.integralLiveData.observe(this, Observer {
            Log.d("tagg", it.toString())
            val res = it.getOrNull()
            if (res != null) {
                if (flag == 0) {
                    integralAll.clear()
                    integralDetail.clear()
                    integralAll.add(res.integralAll)
                    integralDetail.addAll(res.integralDetail.data.datas)
                    adapter.notifyDataSetChanged() // 添加完观察者之后就可以刷新界面,观察者观察的是堆里的数据
                    sr_integralme.isRefreshing = false
                    tvAnimation(tv_integralme_update_in, tv_integralme_update_on, tv_integralme_update_out)
                } else {
                    if (res.integralDetail.data.datas.isEmpty()) {
                        adapter.setFootState(4)
                        loadMoreFlag = 1
                    } else {
                        loading = 0
                        integralDetail.addAll(res.integralDetail.data.datas)
                        adapter.setFootState(3)
                        page++
                    }
                }
            }
        })

        sr_integralme.setOnRefreshListener {
            myIntegralViewModel.getIntegral(0)
            loadMoreFlag = 0
            flag = 0
            page = 2
        }
    }

    private fun loadMore(adapter: IntegralAdapter) {
        rv_myintegral.addOnScrollListener(object : EndRecyclerOnScrollListener() {
            override fun loadMore() {
                if (loadMoreFlag == 0 && loading == 0) {
                    loading = 1
                    adapter.setFootState(2)
                    myIntegralViewModel.getIntegral(page)
                    flag = 1
                }
            }

        })
    }
}