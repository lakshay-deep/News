package com.lakshay.news.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lakshay.news.data.model.News
import com.lakshay.news.data.model.Resource
import com.lakshay.news.data.network.NewsRepository
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel(), NewsRepository.OnGetNewsListener{


    private val _newsLiveData = MutableLiveData<Resource<News>>()
    val newsLiveData: LiveData<Resource<News>>
    get() = _newsLiveData


    fun getNews(){
        _newsLiveData.postValue(Resource.loading())
        newsRepository.getNews(this)
    }

    override fun onGetNewsSuccess(news: News) {
        _newsLiveData.postValue(Resource.success(news))
    }

    override fun onGetNewsFailure(error: Error) {
        _newsLiveData.postValue(Resource.error(error))
    }
}