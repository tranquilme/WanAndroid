package com.cl.androidstudy.ui.system

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.common.FlowLayout
import com.cl.androidstudy.logic.model.SystemKind
import com.cl.navicationtest.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.android.synthetic.main.item_systemdialog_tag.view.*

class BottomDialogAdapter(
    private val data: List<SystemKind.SystemKindData>,
    private val context: Context,
    private val systemViewModel: SystemViewModel,
    private val fragment: SystemFragment
) : RecyclerView.Adapter<BottomDialogAdapter.VH>() {
    var flag = ""
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tv_system_dialog_title)
        val flowLayout = view.findViewById<FlowLayout>(R.id.fl_system_tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_system_dialog, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.d("tagg", flag)
        holder.flowLayout.removeAllViews()
        holder.title.text = data[position].name
        val size = data[position].children.size
        for (i in 0 until size) {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_systemdialog_tag, holder.flowLayout, false)
            view.textView.text = data[position].children[i].name
            if (view.textView.text == flag) {
                view.textView.background = ContextCompat.getDrawable(context, R.drawable.ripple_bg_systemdialog_tag_selected)
                view.textView.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary))
            } else {
                view.textView.background = ContextCompat.getDrawable(context, R.drawable.ripple_bg_systemdialog_tag)
                view.textView.setTextColor(ContextCompat.getColor(context, R.color.appColorPrimary))
            }
            view.textView.setOnClickListener {
                flag = data[position].children[i].name  // 设置标记，表示是上次点击的tag
                view.textView.background = ContextCompat.getDrawable(context, R.drawable.ripple_bg_systemdialog_tag_selected)
                view.textView.setTextColor(ContextCompat.getColor(context, R.color.textColorSecondary))
                systemViewModel.flag = 0
                fragment.sr_system_article.isRefreshing = true
                fragment.bottomDialog.cancel()
                systemViewModel.getSystemArticle(0, data[position].children[i].id)
            }
            holder.flowLayout.addView(view)
        }
    }
}