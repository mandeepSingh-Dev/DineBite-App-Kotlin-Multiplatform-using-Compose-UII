package com.example.kmpapp.core.network

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val error: ApiError) : ApiResult<Nothing>
}

sealed interface ApiError {
    data class Http(val code: Int, val message: String) : ApiError
    data class Network(val message: String = "Check your internet connection.") : ApiError
    data object Serialization : ApiError
    data class Unknown(val message: String = "Something went wrong.") : ApiError
}
