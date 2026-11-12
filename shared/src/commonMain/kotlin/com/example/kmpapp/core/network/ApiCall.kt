package com.example.kmpapp.core.network

import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <T> apiCall(crossinline request: suspend () -> T): ApiResult<T> =
    try {
        ApiResult.Success(request())
    } catch (error: ResponseException) {
        ApiResult.Failure(
            ApiError.Http(
                error.response.status.value,
                error.message ?: "HTTP request failed."
            )
        )
    } catch (_: SerializationException) {
        ApiResult.Failure(ApiError.Serialization)
    } catch (error: CancellationException) {
        throw error
    } catch (error: Throwable) {
        ApiResult.Failure(ApiError.Unknown(error.message ?: "Something went wrong."))
    }
