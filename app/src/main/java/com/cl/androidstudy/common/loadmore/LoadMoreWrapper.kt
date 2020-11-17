package com.cl.androidstudy.common.loadmore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R

class LoadMoreWrapper(private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() { // 因为不同Adapter的ViewHolder类型是不同的，但是都是继承自RecyclerView.ViewHolder，所以泛型指定为RecyclerView.ViewHolder
    private val footView = 1
    private val normalView = 0
    private val footStart = 2
    private val footEnd = 3
    private val footNoMore = 4
    private var footState = 0

    class FootVH(view: View) : RecyclerView.ViewHolder(view) {
        val textFoot = view.findViewById<TextView>(R.id.tv_my_more)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
    }

    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount) {
            return footView
        } else {
            return normalView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == normalView) {
            return adapter.onCreateViewHolder(
                parent,
                viewType
            ) // 调用adapter的onCreateViewHolder返会正常布局
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_more, parent, false)
            return FootVH(
                view
            )
        }
    }

    override fun getItemCount(): Int {
        if (adapter.itemCount != 0) {
            return adapter.itemCount + 1
        } else {
            return 0    // 如果 adapter 中没有数据，不加载footview，如果返回1，则即使没有数据也会加载footview
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FootVH) {
            if (footState == 2) {// 正在加载中
                holder.textFoot.visibility = View.VISIBLE
                holder.progressBar.visibility = View.VISIBLE
            } else if (footState == 3) {// 加载结束
                holder.textFoot.visibility = View.GONE
                holder.progressBar.visibility = View.GONE
            } else if (footState == 4) {// 没有更多数据可以加载了
                holder.progressBar.visibility = View.GONE
                holder.textFoot.text = "没有更多数据啦"
            }
        } else {
            adapter.onBindViewHolder(holder, position)
        }
    }

    fun setFootState(state: Int) {
        footState = state
        notifyDataSetChanged()
    }

}