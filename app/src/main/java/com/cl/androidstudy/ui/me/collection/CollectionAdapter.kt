package com.cl.androidstudy.ui.me.collection

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
import androidx.recyclerview.widget.RecyclerView
import com.cl.androidstudy.MyApplication
import com.cl.androidstudy.R
import com.cl.androidstudy.common.articleitem.ItemViewModel
import com.cl.androidstudy.ext.htmlToString
import com.cl.androidstudy.logic.model.CollectionResponse
import com.cl.androidstudy.ui.web.WebActivity

class CollectionAdapter(
    private val articleDatas: ArrayList<CollectionResponse.CollectionDatas>,
    private val context: Context, private val viewModel: ItemViewModel,
    private val impl: RemoveImpl
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val sharedPreferences = MyApplication.context.getSharedPreferences("collection", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private var collectionId = mutableSetOf<String>()

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_hot_title)
        val author: TextView = view.findViewById(R.id.tv_hot_author)
        val date: TextView = view.findViewById(R.id.tv_hot_date)
        val desc: TextView = view.findViewById(R.id.tv_article_content)
        val collection: ImageView = view.findViewById(R.id.iv_hot_collection)
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
        val date = articleDatas[position].niceDate
        val desc = articleDatas[position].desc
        val author = articleDatas[position].author
        val link = articleDatas[position].link
        val originId = articleDatas[position].originId
        holder as VH
        holder.title.text = title
        holder.date.text = date
        holder.author.text = author
        holder.collection.isSelected = true
        if (desc.isEmpty()) {
            holder.desc.visibility = View.GONE
        } else {
            holder.desc.visibility =
                View.VISIBLE  // RecyclerView 复用问题，如果不重新设置visibility 那么会复用其他的item 导致visibility 是GONE。
            holder.desc.text = desc.htmlToString() // 将html格式转换
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WebActivity::class.java).apply {
                putExtra("link", link)
                putExtra("title", title)
            }
            context.startActivity(intent)
        }
        holder.collection.setOnClickListener {
            it.isSelected = false
            collectionId.remove(originId.toString())
            editor.clear()
            editor.putStringSet("collectionId", collectionId)
            editor.apply()
            viewModel.setUncollection(originId)
            articleDatas.removeAt(position)
            impl.remove(position, articleDatas.size)
            Toast.makeText(context, "取消收藏成功！", Toast.LENGTH_SHORT).show()
        }
    }
}