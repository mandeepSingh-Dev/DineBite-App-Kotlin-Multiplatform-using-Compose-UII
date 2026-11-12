package com.example.kmpapp.core.network.interceptors

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class ApiException(val statusCode: Int, message: String, val errorBody: String?) : Exception(message)

fun HttpClientConfig<*>.installErrorInterceptor(onErrorReceived: (Int, String?) -> Unit = { _, _ -> }) {
    expectSuccess = false
    HttpResponseValidator {
        validateResponse { response ->
            val statusCode = response.status.value
            if (!response.status.isSuccess()) {
                val errorBody = try {
                    response.bodyAsText()
                } catch (_: Throwable) {
                    null
                }
                onErrorReceived(statusCode, errorBody)
                throw ApiException(
                    statusCode = statusCode,
                    message = networkCodeError(statusCode),
                    errorBody = errorBody
                )
            }
        }
    }
}

private fun networkCodeError(statusCode: Int): String {
    return when (statusCode) {
        400 -> "Bad Request. Please check your input."
        401 -> "Unauthorized. Please log in again."
        403 -> "Forbidden. Access is denied."
        404 -> "Resource not found."
        500 -> "Internal Server Error."
        502 -> "Bad Gateway."
        503 -> "Service Unavailable."
        else -> "HTTP Error: $statusCode"
    }
}
