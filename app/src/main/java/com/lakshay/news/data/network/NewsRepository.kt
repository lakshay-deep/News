package com.lakshay.news.data.network

import com.lakshay.news.data.model.News
import com.lakshay.news.util.ApiCallback
import com.lakshay.news.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val newsService: NewsService) {

    fun getNews(onGetNewsListener: OnGetNewsListener) {
        newsService.getNews(Constants.country, Constants.apikey, Constants.pageSize)
            .enqueue(object : ApiCallback<News>() {
                override fun success(response: News) {
                    onGetNewsListener.onGetNewsSuccess(response)
                }

                override fun failure(error: Error) {
                    onGetNewsListener.onGetNewsFailure(error)
                }
            })
    }

    interface OnGetNewsListener {
        fun onGetNewsSuccess(news: News)
        fun onGetNewsFailure(error: Error)
    }
}