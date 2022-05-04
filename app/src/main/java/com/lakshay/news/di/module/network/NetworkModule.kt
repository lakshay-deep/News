package com.lakshay.news.di.module.network

import com.lakshay.news.data.exception.NoConnectivityException
import com.lakshay.news.di.qualifiers.NewsLoggingInterceptor
import com.lakshay.news.di.qualifiers.NewsNetworkInterceptor
import com.lakshay.news.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    companion object {
        private const val CONNECTION_TIMEOUT: Long = 60
        private const val WRITE_TIMEOUT: Long = 60
        private const val READ_TIMEOUT: Long = 60
    }

    @Provides
    @Singleton
    fun okHttpClient(
        @NewsNetworkInterceptor networkInterceptor: Interceptor,
        @NewsLoggingInterceptor loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @NewsLoggingInterceptor
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.i(message) }
        loggingInterceptor.redactHeader("x-auth-token")
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    @NewsNetworkInterceptor
    fun networkInterceptor(networkUtil: NetworkUtil): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            if (!networkUtil.isOnline()) {
                throw NoConnectivityException()
            }
            return@Interceptor chain.proceed(request)
        }
    }

}