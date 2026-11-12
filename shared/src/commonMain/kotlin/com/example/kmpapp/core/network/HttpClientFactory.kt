package com.example.kmpapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface TokenProvider {
    fun accessToken(): String?
}

class EmptyTokenProvider : TokenProvider {
    override fun accessToken(): String? = null
}

fun createHttpClient(tokenProvider: TokenProvider): HttpClient = HttpClient {
    expectSuccess = true

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 30_000
    }
    install(HttpCache)
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) = println(message)
        }
        level = LogLevel.INFO
    }
    defaultRequest {
        url("https://api.example.com/")
        tokenProvider.accessToken()?.let { bearerAuth(it) }
    }
}
