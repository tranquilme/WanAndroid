package com.cl.androidstudy.ui.me.integralrank

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cl.androidstudy.R
import com.cl.androidstudy.base.BaseActivity
import com.cl.androidstudy.logic.model.IntegralRankResponse
import kotlinx.android.synthetic.main.activity_integral_rank.*

class IntegralRankActivity : BaseActivity() {
    private val integralRankViewModel by lazy { ViewModelProvider(this).get(IntegralRankViewModel::class.java) }
    private val rankList = ArrayList<IntegralRankResponse.RankDatas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_integral_rank)
        rv_integral_rank.layoutManager = LinearLayoutManager(this)
        val adapter = IntegralRankAdapter(rankList)
        rv_integral_rank.adapter = adapter
        integralRankViewModel.getIntegranRank()
        sr_rank.setColorSchemeResources(R.color.appColorPrimary)
        sr_rank.isRefreshing = true
        iv_rank_back.setOnClickListener {
            finish()
        }
        integralRankViewModel.integralRankLiveData.observe(this, Observer {
            val res = it.getOrNull()
            if (res != null) {
                rankList.clear()
                rankList.addAll(res.data.datas)
                adapter.notifyDataSetChanged()
                sr_rank.isRefreshing = false
                tvAnimation(tv_rank_update_in, tv_rank_update_on, tv_rank_update_out)
            }
        })
        sr_rank.setOnRefreshListener {
            integralRankViewModel.getIntegranRank()
        }
    }
}

