package com.lakshay.news.di.module.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lakshay.news.data.network.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(ApplicationComponent::class)
class NewsServiceModule {

    companion object {
        private const val BASE_URL_RELEASE = "https://newsapi.org/v2/"
        private const val BASE_URL = BASE_URL_RELEASE
    }

    @Provides
    @Singleton
    fun newsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun gson(): Gson {
        return GsonBuilder().create()
    }
}