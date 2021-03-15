package com.cl.androidstudy.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cl.androidstudy.common.articleitem.CollectState
import com.cl.androidstudy.common.loadmore.LoadMoreWrapper
import com.cl.androidstudy.logic.model.Datas

open class BaseFragment : Fragment() {
    lateinit var loadMoreAdapter: LoadMoreWrapper
    val datas = ArrayList<Datas>()
    private val TAG = "BaseFragment.class"

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: arr")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        if (CollectState.state) {
            CollectState.state = false
            loadMoreAdapter.notifyDataSetChanged()
        }
        Log.d(TAG, "onStart: arr")
        super.onStart()
    }
    

    override fun onResume() {
        Log.d(TAG, "onResume: arr")
        super.onResume()

    }

    override fun onPause() {
        Log.d(TAG, "onPause: arr")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: arr")
        super.onStop()
    }
}