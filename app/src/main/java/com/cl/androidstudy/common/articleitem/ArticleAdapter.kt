package com.cl.androidstudy.common.articleitem

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.ext.htmlToString
import com.cl.androidstudy.logic.model.Datas
import com.cl.androidstudy.ui.web.WebActivity

class ArticleAdapter(
    private val articleDatas: List<Datas>, private val context: Context, private val flag: Int,
    private val viewModel: ItemViewModel
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val sharedPreferences = MyApplication.context.getSharedPreferences("collection", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private var collectionId = mutableSetOf<String>()

    init {
        if (sharedPreferences.getStringSet("collectionId", null) != null) {
            editor.putStringSet("collectionId", collectionId)
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_hot_title)
        val author: TextView = view.findViewById(R.id.tv_hot_author)
        val date: TextView = view.findViewById(R.id.tv_hot_date)
        val desc: TextView = view.findViewById(R.id.tv_article_content)
        val collection: ImageView= view.findViewById(R.id.iv_hot_collection)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return articleDatas.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var flag = 0
        if (flag == 0) {
            flag = 1
            if (sharedPreferences.getStringSet("collectionId", null) != null) {
                sharedPreferences.getStringSet("collectionId", null)
            }
        }
        var collectionFlag = 0 // 0表示该文章未被收藏，1表示已被收藏
        val title = articleDatas[position].title
        val shareUser = articleDatas[position].shareUser
        val date = articleDatas[position].niceDate
        val desc = articleDatas[position].desc
        val author = articleDatas[position].author
        val link = articleDatas[position].link
        val id = articleDatas[position].id
        holder as VH
        if (flag == 0) {     // flag 参数代表标题中是否有 html 标签，0 没有， 1 有
            holder.title.text = title
        } else {
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
            holder.desc.visibility =
                View.VISIBLE  // RecyclerView 复用问题，如果不重新设置visibility 那么会复用其他的item 导致visibility 是GONE。
            holder.desc.text = desc.htmlToString() // 将html格式转换
        }
        if (id.toString() in collectionId) {
            collectionFlag = 1
        }
        holder.collection.isSelected = id.toString() in collectionId
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java).apply {
                putExtra("link", link)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
        holder.collection.setOnClickListener {// 点击收藏或者取消收藏
            if (collectionFlag == 0) {  // 未收藏
                collectionFlag = 1
                it.isSelected = true
                collectionId.add(id.toString())
                editor.clear()
                editor.putStringSet("collectionId", collectionId)
                editor.apply()
                viewModel.setCollection(id)
                Toast.makeText(context, "收藏成功！", Toast.LENGTH_SHORT).show()
            } else {
                collectionFlag = 0
                it.isSelected = false
                collectionId.remove(id.toString())
                editor.clear()
                editor.putStringSet("collectionId", collectionId)
                editor.apply()
                viewModel.setUncollection(id)
                Toast.makeText(context, "取消收藏成功！", Toast.LENGTH_SHORT).show()
            }
        }
    }
}