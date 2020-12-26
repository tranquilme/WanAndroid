package com.cl.androidstudy.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cl.androidstudy.MainActivity
import com.cl.androidstudy.R
import com.cl.androidstudy.ui.home.gzh.GzhFragment
import com.cl.androidstudy.ui.home.hot.HotFragment
import com.cl.androidstudy.ui.home.project.ProjectFragment
import com.cl.androidstudy.ui.home.search.SearchActivity
import com.cl.androidstudy.ui.home.square.SquareFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.abs

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment.class"
    private var currentOffset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { // 监听appbar的动作，实现底部导航栏的显示与隐藏
                _, offset ->
            if (offset != currentOffset && abs(currentOffset - offset) > 25) { // 如果appbar的偏移量与最新的偏移量不一样且绝对值大于25
                (activity as MainActivity).bottomAnimation(currentOffset < offset) // currentOffset < offset 代表向下滑
                currentOffset = offset
            }
        })


        iv_search.setOnClickListener {
            val keywords = et_search.text.toString()
            if (keywords.isEmpty()) {
                Toast.makeText(activity, "关键字不能为空！", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(activity, SearchActivity::class.java)
                intent.putExtra("keywords", et_search.text.toString())
                startActivity(intent)
            }
        }
        viewPager2_home.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 4

            override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> HotFragment()
                    1 -> ProjectFragment()
                    2 -> GzhFragment()
                    else -> SquareFragment()
                }

        }
        TabLayoutMediator(tabLayout_home, viewPager2_home) { tab, position ->
            when (position) {
                0 -> tab.text = "热门"
                1 -> tab.text = "项目"
                2 -> tab.text = "公众号"
                else -> tab.text = "广场"
            }
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        et_search.text = null
    }
}