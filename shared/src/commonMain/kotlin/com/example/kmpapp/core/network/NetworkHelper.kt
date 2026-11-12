package com.example.kmpapp.core.network

import com.example.kmpapp.core.network.models.ApiResponse2
import com.example.kmpapp.core.network.models.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.bodyAsText
import kotlinx.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.milliseconds

fun isNetworkException(e: Throwable): Boolean =
    e is IOException ||
            e is SocketTimeoutException ||
            e is ConnectTimeoutException ||
            e is HttpRequestTimeoutException

fun networkCodeError(statusCode: Int): String {
    return when (statusCode) {
        400 -> "Invalid request. Please Try again."
        401 -> "Unauthorized access. Login again!"
        402 -> "Payment Required"
        403 -> "You do not have permission to access this resource."
        404 -> "The requested resource could not be found."
        500, 502 -> "Server Not Responding. Please try again later."
        503 -> "Service is currently unavailable. Please try again later."
        504 -> "The server is taking too long to respond. Please try again later."
        else -> "HTTP Error: $statusCode"
    }
}

fun handleUseCaseException(e: Throwable): String {
    return when (e) {
        is ResponseException -> {
            networkCodeError(e.response.status.value)
        }
        is HttpRequestTimeoutException,
        is SocketTimeoutException,
        is ConnectTimeoutException -> {
            "Connection Error: Unable to establish a connection to the server."
        }
        is IOException -> {
            "Can’t reach the server right now. Please try again later."
        }
        is SerializationException -> {
            "Data parsing error. Please try again."
        }
        else -> {
            val msg = e.message
            if (msg?.contains("Unable to resolve host", ignoreCase = true) == true ||
                msg?.contains("Failed to connect", ignoreCase = true) == true
            ) {
                "Can’t reach the server right now. Please try again later."
            } else {
                e.message ?: "Something went wrong. Please try again."
            }
        }
    }
}

class NetworkHelper(
    val httpClient: HttpClient,
    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }
) {
    inline fun <reified T> executeWithFlow(
        crossinline call: suspend () -> T,
        crossinline onSuccess: suspend (response: T) -> String? = { null },
        crossinline onError: suspend (exception: String) -> Unit = {}
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading())

        try {
            val response = call()
            val exception = onSuccess(response)
            if (exception == null) {
                emit(NetworkResult.Success(response))
            } else {
                onError(exception)
                emit(NetworkResult.Error(exception))
            }
        } catch (e: ResponseException) {
            val statusCode = e.response.status.value
            val errorBodyString = try {
                e.response.bodyAsText()
            } catch (_: Throwable) {
                null
            }

            val errorResponse = errorBodyString?.let { parseError(it) }
            val errorMessage = if (!errorResponse?.message.isNullOrBlank()) {
                errorResponse.message
            } else {
                networkCodeError(statusCode)
            }

            onError(errorMessage)
            emit(NetworkResult.Error(error = errorMessage, errorCode = statusCode, bodyData = errorResponse))
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            val errorMessage = handleUseCaseException(e)
            onError(errorMessage)
            emit(NetworkResult.Error(errorMessage))
        }
    }.catch { t ->
        val errorMessage = handleUseCaseException(t)
        onError(errorMessage)
        emit(NetworkResult.Error(errorMessage))
    }.flowOn(Dispatchers.Default)

    inline fun <reified T> executeWithRetryFlow(
        crossinline call: suspend () -> T,
        crossinline onSuccess: suspend (response: T) -> String? = { null },
        crossinline onError: suspend (exception: String) -> Unit = {},
        shouldLoading: Boolean = true,
        maxRetries: Int = 3
    ): Flow<NetworkResult<T>> = flow {
        if (shouldLoading) {
            emit(NetworkResult.Loading())
        }

        try {
            val response = call()
            val exception = onSuccess(response)
            if (exception == null) {
                emit(NetworkResult.Success(response))
            } else {
                onError(exception)
                emit(NetworkResult.Error(exception))
            }
        } catch (e: ResponseException) {
            val statusCode = e.response.status.value
            val errorBodyString = try {
                e.response.bodyAsText()
            } catch (_: Throwable) {
                null
            }

            val errorResponse = errorBodyString?.let { parseError(it) }
            val errorMessage = if (!errorResponse?.message.isNullOrBlank()) {
                errorResponse.message
            } else {
                networkCodeError(statusCode)
            }

            onError(errorMessage)
            emit(NetworkResult.Error(error = errorMessage, errorCode = statusCode, bodyData = errorResponse))
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            val errorMessage = handleUseCaseException(e)
            onError(errorMessage)
            emit(NetworkResult.Error(errorMessage))
        }
    }.retryWhen { throwable, count ->
        if (isNetworkException(throwable) && count < maxRetries) {
            delay((1000 * (count + 1)).milliseconds)
            true
        } else {
            false
        }
    }.catch { t ->
        val errorMessage = handleUseCaseException(t)
        onError(errorMessage)
        emit(NetworkResult.Error(errorMessage))
    }.flowOn(Dispatchers.Default)

    inline fun <reified T> executeWithResponseManipulate(
        crossinline call: suspend () -> T,
        crossinline onSuccess: suspend (response: T) -> T = { it },
        crossinline onError: suspend (exception: String) -> Unit = {},
        shouldLoading: Boolean = true,
        maxRetries: Int = 3
    ): Flow<NetworkResult<T>> = flow {
        if (shouldLoading) {
            emit(NetworkResult.Loading())
        }

        try {
            val response = call()
            val updatedResponse = onSuccess(response)
            emit(NetworkResult.Success(updatedResponse))
        } catch (e: ResponseException) {
            val statusCode = e.response.status.value
            val errorBodyString = try {
                e.response.bodyAsText()
            } catch (_: Throwable) {
                null
            }

            val errorResponse = errorBodyString?.let { parseError(it) }
            val errorMessage = if (!errorResponse?.message.isNullOrBlank()) {
                errorResponse.message
            } else {
                networkCodeError(statusCode)
            }

            onError(errorMessage)
            emit(NetworkResult.Error(error = errorMessage, errorCode = statusCode, bodyData = errorResponse))
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            val errorMessage = handleUseCaseException(e)
            onError(errorMessage)
            emit(NetworkResult.Error(errorMessage))
        }
    }.retryWhen { throwable, count ->
        if (isNetworkException(throwable) && count < maxRetries) {
            delay((1000 * (count + 1)).milliseconds)
            true
        } else {
            false
        }
    }.catch { t ->
        val errorMessage = handleUseCaseException(t)
        onError(errorMessage)
        emit(NetworkResult.Error(errorMessage))
    }.flowOn(Dispatchers.Default)

    fun parseError(rawBody: String): ErrorResponse? {
        return try {
            json.decodeFromString<ErrorResponse>(rawBody)
        } catch (_: Throwable) {
            try {
                val apiResponse = json.decodeFromString<ApiResponse2>(rawBody)
                ErrorResponse(status = apiResponse.status, message = apiResponse.message)
            } catch (_: Throwable) {
                null
            }
        }
    }
}
