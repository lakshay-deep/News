package com.lakshay.news.di.module.libraries

import android.content.Context
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class PicassoModule {

    @Provides
    @Singleton
    fun picasso(
        @ApplicationContext context: Context
    ): Picasso {
        return Picasso.Builder(context)
            .build()
    }
}