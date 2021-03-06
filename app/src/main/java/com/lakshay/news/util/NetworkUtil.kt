package com.lakshay.news.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtil @Inject constructor(@ApplicationContext context: Context) {

    private val connectivityManager: ConnectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

    fun isOnline(): Boolean {
        networkInfo = connectivityManager.activeNetworkInfo
        networkInfo?.let {
            return it.isConnected
        } ?: run { return false }
    }
}