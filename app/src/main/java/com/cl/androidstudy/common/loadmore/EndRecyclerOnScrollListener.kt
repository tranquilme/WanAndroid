package com.cl.androidstudy.common.loadmore

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndRecyclerOnScrollListener: RecyclerView.OnScrollListener(){
    private var flag = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layout = recyclerView.layoutManager as LinearLayoutManager
        val lastPositionCompletely = layout.findLastCompletelyVisibleItemPosition()
        if (lastPositionCompletely == layout.itemCount - 1) {
            loadMore()
        }
    }

    abstract fun loadMore()
}