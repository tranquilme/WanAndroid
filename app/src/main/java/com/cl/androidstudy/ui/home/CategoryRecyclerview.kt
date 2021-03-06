package com.cl.androidstudy.ui.home

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cl.androidstudy.MyApplication

class CategoryRecyclerview(context: Context, attributeSet: AttributeSet): RecyclerView(context, attributeSet){
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        when(ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}