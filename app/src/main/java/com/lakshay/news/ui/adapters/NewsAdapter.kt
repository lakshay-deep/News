package com.lakshay.news.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lakshay.news.R
import com.lakshay.news.data.model.Article
import com.lakshay.news.ui.viewholders.NewsViewHolder
import com.lakshay.news.util.DateTimeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_news.view.*

class NewsAdapter(private val picasso: Picasso) : ListAdapter<Article,NewsViewHolder>(
    differCallback), NewsViewHolder.OnClickListener {

    private lateinit var onClickListener: OnClickListener

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cell_news,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position).let { article ->
            holder.setNews(article, picasso)
            holder.setOnClickListener(this)
        }
    }

    override fun onClick(position: Int, url: String?) {
        onClickListener.onClick(position, url)
    }

    interface OnClickListener {
        fun onClick(position: Int, url: String?)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}
