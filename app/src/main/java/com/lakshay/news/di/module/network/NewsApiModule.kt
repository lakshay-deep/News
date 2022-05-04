package com.lakshay.news.di.module.network

import com.lakshay.news.data.network.NewsRepository
import com.lakshay.news.data.network.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module(includes = [NewsServiceModule::class])
@InstallIn(ApplicationComponent::class)
class NewsApiModule {

    @Provides
    @Singleton
    fun newsApi(newsService: NewsService): NewsRepository {
        return NewsRepository(newsService)
    }
}