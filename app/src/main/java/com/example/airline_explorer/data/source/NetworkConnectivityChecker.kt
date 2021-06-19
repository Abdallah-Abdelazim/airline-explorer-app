package com.example.airline_explorer.data.source

interface NetworkConnectivityChecker {

    fun isOnline(): Boolean
}