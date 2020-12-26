package com.cl.androidstudy.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.Datas

open class BaseFragment : Fragment() {
    lateinit var loadMoreAdapter: LoadMoreWrapper
    val datas = ArrayList<Datas>()

    /**
     * 先删除数据，在添加数据，目的是为了让数据变化，调用notifyDataSetChanged方法刷新Recyclerview，
     * 即重新调用onBindViewHolder方法刷新数据。
     */
    override fun onResume() {
        super.onResume()
        val datasCopy = ArrayList<Datas>()
        datasCopy.addAll(datas)
        datas.clear()
        loadMoreAdapter.notifyDataSetChanged()
        datas.addAll(datasCopy)
        loadMoreAdapter.notifyDataSetChanged()
    }

    fun tvAnimation(tv_update_in: TextView, tv_update_on: TextView, tv_update_out: TextView) {  // tv 淡入动画
        tv_update_in.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_update_in.visibility = View.GONE
                        tvOnAnimation(tv_update_on, tv_update_out)
                    }
                })
        }
    }

    fun tvOnAnimation(tv_update_on: TextView, tv_update_out: TextView) {   // tv持续动画
        tv_update_on.visibility = View.VISIBLE
        tv_update_on.apply {
            alpha = 1f
            animate().alpha(1f)
                .setDuration(800)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_update_on.visibility = View.GONE
                        tvOutAnimation(tv_update_out)
                    }
                })
        }
    }

    fun tvOutAnimation(tv_update_out: TextView) {  // tv淡出动画
        tv_update_out.visibility = View.VISIBLE
        tv_update_out.apply {
            alpha = 1f
            animate().alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_update_out.visibility = View.GONE
                    }
                })
        }
    }
}