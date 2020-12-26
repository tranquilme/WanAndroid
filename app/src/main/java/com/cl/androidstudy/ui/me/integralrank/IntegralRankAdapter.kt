package com.cl.androidstudy.ui.me.integralrank

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.logic.model.IntegralRankResponse

class IntegralRankAdapter(val rankList: List<IntegralRankResponse.RankDatas>): RecyclerView.Adapter<IntegralRankAdapter.VH>(){
    inner class VH(view: View): RecyclerView.ViewHolder(view){
        val tvNum = view.findViewById<TextView>(R.id.tv_rank_num)
        val tvUsername = view.findViewById<TextView>(R.id.tv_rank_username)
        val tvCount = view.findViewById<TextView>(R.id.tv_rank_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_integral_rank, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return rankList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val rankData = rankList[position]
        holder.tvNum.text = rankData.rank
        holder.tvUsername.text = rankData.username
        holder.tvCount.text = rankData.coinCount.toString()
    }
}