package com.example.kmpapp.core.network

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null,
    val error: String? = null,
    val errorCode: Int? = null,
    val bodyData: Any? = null
) {
    class Loading<T> : NetworkResult<T>(data = null, message = null, error = null, errorCode = null, bodyData = null)
    class Success<T>(data: T?, message: String? = null) : NetworkResult<T>(data = data, message = message, error = null, errorCode = null, bodyData = null)
    class Error<T>(error: String?, errorCode: Int? = null, bodyData: Any? = null) : NetworkResult<T>(data = null, message = null, error = error, errorCode = errorCode, bodyData = bodyData)
}

fun <T> NetworkResult<T>.getCallbacks(
    onLoading: () -> Unit = {},
    onSuccess: (NetworkResult.Success<T>) -> Unit,
    onError: (NetworkResult.Error<T>) -> Unit
) {
    when (this) {
        is NetworkResult.Loading -> onLoading()
        is NetworkResult.Success -> onSuccess(this)
        is NetworkResult.Error -> onError(this)
    }
}
