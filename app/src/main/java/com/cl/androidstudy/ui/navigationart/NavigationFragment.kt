package com.cl.androidstudy.ui.navigationart

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.MainActivity
import com.cl.androidstudy.R
import com.cl.androidstudy.logic.model.NavigationResponse
import kotlinx.android.synthetic.main.activity_web.view.*
import kotlinx.android.synthetic.main.fragment_navigation.*
import kotlinx.android.synthetic.main.fragment_navigation.tv_navigation_update_in
import kotlinx.android.synthetic.main.fragment_navigation.tv_navigation_update_on
import kotlinx.android.synthetic.main.fragment_navigation.tv_navigation_update_out

class NavigationFragment : Fragment() {
    private val navigationViewModel by lazy { ViewModelProvider(this).get(NavigationViewModel::class.java) }
    private val navigationList = ArrayList<NavigationResponse.NavigationData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_navigation.layoutManager = LinearLayoutManager(activity)
        val navigationAdapter = NavigationAdapter(navigationList, requireActivity())
        rv_navigation.adapter = navigationAdapter
        sr_navigation_update.setColorSchemeResources(R.color.tagColorPrimary)
        sr_navigation_update.isRefreshing = true
        navigationViewModel.update()
        navigationViewModel.navigationLiveData.observe(viewLifecycleOwner, Observer {
            val res = it.getOrNull()
            if (res != null) {
                navigationList.clear()
                navigationList.addAll(res.data)
                navigationAdapter.notifyDataSetChanged()
                sr_navigation_update.isRefreshing = false
                tvAnimation()
            }
        })

        rv_navigation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        sr_navigation_update.setOnRefreshListener {
            navigationViewModel.update()
            sr_navigation_update.isRefreshing = true
        }
    }

    fun tvAnimation() {  // tv 淡入动画
        tv_navigation_update_in.apply {
            visibility = View.VISIBLE
            alpha = 0f
            animate().alpha(1f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_navigation_update_in.visibility = View.GONE
                        tvOnAnimation()
                    }
                })
        }
    }

    fun tvOnAnimation() {   // tv持续动画
        tv_navigation_update_on.visibility = View.VISIBLE
        tv_navigation_update_on.apply {
            alpha = 1f
            animate().alpha(1f)
                .setDuration(800)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_navigation_update_on.visibility = View.GONE
                        tvOutAnimation()
                    }
                })
        }
    }

    fun tvOutAnimation() {  // tv淡出动画
        tv_navigation_update_out.visibility = View.VISIBLE
        tv_navigation_update_out.apply {
            alpha = 1f
            animate().alpha(0f)
                .setDuration(500)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        tv_navigation_update_out.visibility = View.GONE
                    }
                })
        }
    }
}