package com.lakshay.news.ui.viewholders

import android.view.View
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lakshay.news.R
import com.lakshay.news.data.model.Article
import com.lakshay.news.util.DateTimeUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_news.view.*

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var onClickListener: OnClickListener

    fun setNews(article: Article, picasso: Picasso) {
        itemView.setOnClickListener {
            onClickListener.onClick(adapterPosition, article.url)
        }
        itemView.tvTitle.text = article.title
        itemView.tvDescription.text = article.description
        itemView.tvSite.text = article.source.name
        itemView.tvTimeAgo.text = "${DateTimeUtils.getHoursAgoFromDate(article.publishedAt)}"
        article.source.name.split('.')[0].let {
            it.split(' ')[0].let {
                itemView.tvTag.text = it
            }
        }
        picasso.load(article.urlToImage)
            .error(R.drawable.ic_news_placeholder_small)
            .placeholder(R.drawable.ic_news_placeholder_small)
            .into(itemView.ivImage)
    }

    interface OnClickListener {
        fun onClick(position: Int, url: String?)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
}