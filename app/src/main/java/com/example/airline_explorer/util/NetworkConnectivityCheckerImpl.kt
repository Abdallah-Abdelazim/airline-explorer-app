package com.example.airline_explorer.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.airline_explorer.data.source.NetworkConnectivityChecker

class NetworkConnectivityCheckerImpl(private val context: Context) : NetworkConnectivityChecker {

    override fun isOnline(): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}