package com.example.kmpapp.core.network

import kotlinx.coroutines.flow.StateFlow

expect class NetworkConnectivityObserver() {
    val isConnected: StateFlow<Boolean>
}
