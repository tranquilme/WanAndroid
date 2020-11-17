package com.cl.androidstudy.ui.navigationart

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
import com.cl.androidstudy.R
import com.cl.androidstudy.logic.model.NavigationResponse
import kotlinx.android.synthetic.main.activity_web.view.*
import kotlinx.android.synthetic.main.fragment_navigation.*

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
        navigationViewModel.update()
        navigationViewModel.navigationLiveData.observe(viewLifecycleOwner, Observer {
            val res = it.getOrNull()
            if (res != null) {
                navigationList.clear()
                navigationList.addAll(res.data)
                navigationAdapter.notifyDataSetChanged()
            }
        })
//        rv_navigation.addOnScrollListener()
    }
}