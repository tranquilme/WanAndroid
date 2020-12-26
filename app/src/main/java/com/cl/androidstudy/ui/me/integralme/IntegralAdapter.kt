package com.cl.androidstudy.ui.me.integralme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.IntegralAll
import com.cl.androidstudy.logic.model.IntegralDetail

class IntegralAdapter(
    private val integralAll: List<IntegralAll>,
    private val data: List<IntegralDetail.IntegralDetailDatas>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val topView = -1
    private val normalView = 0
    private val footView = 1
    private var footState = 0

    inner class TopVH(view: View) : RecyclerView.ViewHolder(view) {
        val allIntegral = view.findViewById<TextView>(R.id.tv_integraltop_all)
        val level = view.findViewById<TextView>(R.id.tv_integraltop_level)
        val rank = view.findViewById<TextView>(R.id.tv_integraltop_rank)
    }

    inner class NormalVH(view: View) : RecyclerView.ViewHolder(view) {
        val reason = view.findViewById<TextView>(R.id.tv_myintegral_reason)
        val desc = view.findViewById<TextView>(R.id.tv_myintegral_desc)
        val coinCount = view.findViewById<TextView>(R.id.tv_myintegral_coinCount)
    }

    inner class FootVH(view: View) : RecyclerView.ViewHolder(view) {
        val textFoot = view.findViewById<TextView>(R.id.tv_my_more)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) {
            0
        } else {
            data.size + 2
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount) {
            return footView
        } else if (position == 0) {
            return topView
        } else {
            return normalView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == -1) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_integral_top, parent, false)
            TopVH(view)
        } else if (viewType == 0) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_integral_normal, parent, false)
            NormalVH(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_more, parent, false)
            return LoadMoreWrapper.FootVH(
                view
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TopVH && integralAll.isNotEmpty()) {
            holder.allIntegral.text = integralAll[0].data.coinCount.toString()
            val level = integralAll[0].data.level
            val rank = integralAll[0].data.rank
            holder.level.text = "等级:$level"
            holder.rank.text = "排名:$rank"
        }else if (holder is NormalVH) {
            holder.reason.text = data[position - 1].reason
            holder.desc.text = data[position - 1].desc.substring(0, 19)
            val coinCount = data[position - 1].coinCount
            holder.coinCount.text = "+$coinCount"
        }else if(holder is LoadMoreWrapper.FootVH) {
            when(footState) {
                2 -> {
                    holder.textFoot.text = "加载中..."
                    holder.textFoot.visibility = View.VISIBLE
                    holder.progressBar.visibility = View.VISIBLE
                }
                3 -> {
                    holder.textFoot.visibility = View.GONE
                    holder.progressBar.visibility = View.GONE
                }
                4 -> {
                    holder.progressBar.visibility = View.GONE
                    holder.textFoot.text = "没有更多数据啦"
                }
            }
        }
    }

    fun setFootState(state: Int) {
        footState = state
        notifyDataSetChanged()
    }
}