package com.example.kmpapp.core.network.interceptors

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

fun HttpClientConfig<*>.installLoggingInterceptor(tag: String = "DineBiteHttpLog") {
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("[$tag] $message")
            }
        }
        level = LogLevel.ALL
    }
}
