package com.cl.androidstudy.ui.navigationart

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.logic.model.NavigationResponse
import com.cl.androidstudy.ui.web.WebActivity

class NavigationAdapter(private val navigationData: List<NavigationResponse.NavigationData>, val context: Context): RecyclerView.Adapter<NavigationAdapter.VH>(){
    inner class VH(view: View): RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tv_navigation_title)
        val flowLayout = view.findViewById<FlowLayout>(R.id.flow_navigation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_navigation, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return navigationData.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.flowLayout.removeAllViews()  // item 复用问题
        val data = navigationData[position]
        holder.title.text = data.name
        for (i in data.articles.indices) {
            val view = LayoutInflater.from(context).inflate(R.layout.item_navigation_article, holder.flowLayout,false) as TextView
            view.text = data.articles[i].title
            var link = data.articles[i].link
            if (link[4] != 's') { // 如果 Link 是http开头的就要转化为https开头
                val s = CharArray(link.length + 1)
                s[0] = 'h'
                s[1] = 't'
                s[2] = 't'
                s[3] = 'p'
                s[4] = 's'
                for (j in 5 .. link.length) {
                    s[j] = link[j - 1]
                }
                link = String(s)
            }
            view.setOnClickListener {
                val intent = Intent(context, WebActivity::class.java).apply {// 此lambda函数中有intent对象的上下文
                    putExtra("title", data.articles[i].title)
                    putExtra("link", link)
                }
                context.startActivity(intent)
            }
            holder.flowLayout.addView(view)
        }
    }
}