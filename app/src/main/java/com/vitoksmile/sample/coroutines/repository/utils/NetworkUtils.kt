package com.vitoksmile.sample.coroutines.repository.utils

import android.content.Context
import android.net.ConnectivityManager
import com.vitoksmile.sample.coroutines.repository.Application

object NetworkUtils {

    /**
     * Check if network is available
     */
    fun isAvailable(): Boolean {
        val manager = Application.appContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo?.isConnected == true
    }
}

/**
 * Check if network is available
 */
fun isNetworkAvailable() = NetworkUtils.isAvailable()