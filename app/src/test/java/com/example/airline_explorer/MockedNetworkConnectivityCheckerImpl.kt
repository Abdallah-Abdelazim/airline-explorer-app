package com.example.airline_explorer

import com.example.airline_explorer.data.source.NetworkConnectivityChecker

class MockedNetworkConnectivityCheckerImpl : NetworkConnectivityChecker {
    override fun isOnline(): Boolean {
        return true
    }
}