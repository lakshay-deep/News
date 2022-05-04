package com.lakshay.news.di.module.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NewsAppModule {

    @Provides
    @Singleton
    fun timberTree(): Timber.Tree{
        return Timber.DebugTree()
    }
}