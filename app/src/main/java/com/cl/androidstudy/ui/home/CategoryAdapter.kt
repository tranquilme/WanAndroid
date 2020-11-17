package com.cl.androidstudy.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.logic.model.CategoryResponse
import com.cl.androidstudy.ui.home.gzh.GzhViewModel
import com.cl.androidstudy.ui.home.project.ProjectViewModel

class CategoryAdapter(private val categoryData: ArrayList<CategoryResponse.Result>,private val viewModel: ViewModel, private val flag: Int): RecyclerView.Adapter<CategoryAdapter.CategoryVH>(){
    private var checkedPosition = 0

    inner class CategoryVH(view: View): RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<CheckedTextView>(R.id.category_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryVH(view)
    }

    override fun onBindViewHolder(holder: CategoryVH, position: Int) {
        holder.textView.text = categoryData[position].name
        holder.textView.isChecked = position == checkedPosition
        val cid = categoryData[position].id
        holder.textView.setOnClickListener {
            checkedPosition = position
            notifyDataSetChanged()
            if (flag == 0) {
                viewModel as ProjectViewModel
                viewModel.flag.value = 1 // 只要执行赋值语句，就可以触发LiveData监听事件。
                viewModel.cid = cid
                viewModel.queryData(1, cid)
            } else {
                viewModel as GzhViewModel
                viewModel.flag.value = 1 // 只要执行赋值语句，就可以触发LiveData监听事件。
                viewModel.cid = cid
                viewModel.queryData(1, cid)
            }

        }
    }

    override fun getItemCount(): Int {
        return categoryData.size
    }
}