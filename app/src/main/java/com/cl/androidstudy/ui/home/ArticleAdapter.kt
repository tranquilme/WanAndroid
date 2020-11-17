package com.cl.androidstudy.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.R
import com.cl.androidstudy.ext.htmlToString
import com.cl.androidstudy.logic.model.ArticleResponse
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_home_hot.*

class ArticleAdapter(private val articleDatas: List<Datas>, private val context: Context, private val flag: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class VH(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_hot_title)
        val author: TextView = view.findViewById(R.id.tv_hot_author)
        val date: TextView = view.findViewById(R.id.tv_hot_date)
        val desc: TextView = view.findViewById(R.id.tv_article_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return articleDatas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val title = articleDatas[position].title
        val shareUser = articleDatas[position].shareUser
        val date = articleDatas[position].niceDate
        val desc = articleDatas[position].desc
        val author = articleDatas[position].author
        val link = articleDatas[position].link
        holder as VH
        if (flag == 0){     // flag 参数代表标题中是否有 html 标签，0 没有， 1 有
            holder.title.text = title
        }else {
            holder.title.text = title.htmlToString()
        }
        holder.date.text = date
        if (shareUser.isEmpty()) {
            holder.author.text = author
        } else {
            holder.author.text = shareUser
        }
        if (desc.isEmpty()) {
            holder.desc.visibility = View.GONE
        } else {
            holder.desc.visibility = View.VISIBLE  // RecyclerView 复用问题，如果不重新设置visibility 那么会复用其他的item 导致visibility 是GONE。
            holder.desc.text = desc.htmlToString() // 将html格式转换
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java).apply {
                putExtra("link", link)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
    }
}