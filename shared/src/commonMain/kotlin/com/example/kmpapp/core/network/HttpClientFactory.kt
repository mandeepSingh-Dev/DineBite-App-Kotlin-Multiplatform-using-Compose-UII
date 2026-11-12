package com.example.kmpapp.core.network

import com.example.kmpapp.core.network.interceptors.installErrorInterceptor
import com.example.kmpapp.core.network.interceptors.installLoggingInterceptor
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.bearerAuth
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface TokenProvider {
    fun accessToken(): String?
}

class EmptyTokenProvider : TokenProvider {
    override fun accessToken(): String? = null
}

fun createHttpClient(
    tokenProvider: TokenProvider,
    baseUrl: String = "https://ai.appsstart.com/dinebite/api/",
    onError: (Int, String?) -> Unit = { _, _ -> }
): HttpClient = HttpClient {
    installLoggingInterceptor("DineBiteNetwork")
    installErrorInterceptor(onError)

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


    defaultRequest {
        url(baseUrl)
        contentType(ContentType.Application.Json)
        tokenProvider.accessToken()?.let { bearerAuth(it) }
    }
}
