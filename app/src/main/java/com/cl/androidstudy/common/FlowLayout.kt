package com.cl.androidstudy.common

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class FlowLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams { // 重写ViewGroup此方法，在 inflate 解析xml 生成View时，为View生成 MarginLayoutParams 对象
        return MarginLayoutParams(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) { // 测量 当前ViewGroup 宽高 以及子View 宽高
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)  // 获取父View的测量宽度
        measureChildren(widthMeasureSpec, heightMeasureSpec)    // 测量所有子View
        var currentHeight = 0
        var currentWidth = 0
        if (childCount > 0) {
            val lp = getChildAt(0).layoutParams as MarginLayoutParams // 因为重写了 generateLayoutParams 方法所以生成 MarginLayoutParams
            currentHeight = getChildAt(0).measuredHeight + lp.topMargin + lp.bottomMargin // 当前行高
        }
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            if (currentWidth + child.measuredWidth + lp.leftMargin + lp.rightMargin < widthSize) { // 不用换行
                currentWidth += child.measuredWidth
            } else {    // 换行
                currentWidth = child.measuredWidth
                currentHeight += child.measuredHeight + lp.topMargin + lp.bottomMargin
            }
        }
        setMeasuredDimension(widthSize, currentHeight) // 设置ViewGroup宽 高
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) { // 布局子View
        val width = r - l; // ViewGroup 宽度
        var currentWidth = 0 // 当前行的宽度
        var currentHeight = 0   // 当前行高
        var cLeft = 0
        var cTop = 0
        var cRight = 0
        var cBottom = 0 // 子View 的左上 右下坐标
        var marginLeft = 0
        var marginRight = 0
        var marginTop = 0
        var marginBottom = 0 // 子View的Margin 属性

        if (childCount > 0) {
            val lp = getChildAt(0).layoutParams as MarginLayoutParams // 所有的子View margin属性都相同
            marginLeft = lp.leftMargin
            marginRight = lp.rightMargin
            marginTop = lp.topMargin
            marginBottom = lp.bottomMargin
        }

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            cLeft = marginLeft + currentWidth
            cTop = marginTop + currentHeight
            if (currentWidth + child.measuredWidth + marginLeft + marginRight < width) { // 不换行
                currentWidth += child.measuredWidth + marginLeft + marginRight
            } else {     // 换行
                currentWidth = child.measuredWidth + marginLeft + marginRight
                currentHeight += child.measuredHeight + marginTop + marginBottom
                cLeft = marginLeft
                cTop = marginTop + currentHeight
            }
            cRight = cLeft +child.measuredWidth
            cBottom = cTop + child.measuredHeight
            child.layout(cLeft, cTop, cRight, cBottom) // 为子View布局，或者说在ViewGroup中放置子View
            cLeft += child.measuredWidth
        }
    }

}