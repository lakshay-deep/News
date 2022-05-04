package com.lakshay.news.di.module.util

import android.content.Context
import com.lakshay.news.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class UtilsModule {

    @Provides
    @ActivityScoped
    fun networkUtil(context: Context): NetworkUtil {
        return NetworkUtil(context)
    }
}