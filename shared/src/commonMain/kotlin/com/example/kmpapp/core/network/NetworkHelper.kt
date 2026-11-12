package com.example.kmpapp.core.network

import com.example.kmpapp.core.network.models.ApiResponse2
import com.example.kmpapp.core.network.models.ErrorResponse
import com.example.kmpapp.core.utils.fromJson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

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
        crossinline call: suspend () -> HttpResponse,
        crossinline onSuccess: suspend (response: HttpResponse) -> String?,
        crossinline onError: suspend (exception: String) -> Unit
    ): Flow<NetworkResult<T>> = flow {

        emit(NetworkResult.Loading())

        val response = call()

        // In Ktor (with expectSuccess = true), reaching this line means 2xx Success
        val exception = onSuccess(response)
        val result = if (exception == null) {
            NetworkResult.Success(response.body<T>())
        } else {
            NetworkResult.Error(exception)
        }

        emit(result)

    }.catch { e ->

        //handling Response exception here Because Ktor throw direct Response exception not like Retrofit which gives backend api error in response body
        when (e) {
            is ResponseException -> {
                val statusCode = e.response.status.value
                val errorBody = try {
                    e.response.bodyAsText().fromJson<ErrorResponse>()
                } catch (parseEx: Exception) {
                    null
                }

                println("flbmkfmvf ->  ${errorBody.toString()}")
                println("flbmkfmvf-> ${statusCode}")

                val errorResult: NetworkResult<T> = when {
                    statusCode == HttpStatusCode.BadRequest.value && errorBody?.status == 0 -> {
                        NetworkResult.Error(handleUseCaseException(Exception(errorBody.message)))
                    }
                    statusCode == HttpStatusCode.Forbidden.value && errorBody?.status == 0 -> {
                        NetworkResult.Error(handleUseCaseException(Exception(errorBody.message)))
                    }
                    statusCode == HttpStatusCode.Unauthorized.value -> {
                        // clearAllUserData()
                        NetworkResult.Error(
                            error = handleUseCaseException(e),
                            errorCode = statusCode
                        )
                    }
                    else -> {
                        NetworkResult.Error(
                            error = handleUseCaseException(Exception(errorBody?.message)),
                            errorCode = statusCode
                        )
                    }
                }
                emit(errorResult)
            }

            else -> {
                // Handles all other exceptions (IOException, SerializationException, etc.)
                emit(NetworkResult.Error(handleUseCaseException(e)))
            }
        }
    }.flowOn(Dispatchers.IO)
}
