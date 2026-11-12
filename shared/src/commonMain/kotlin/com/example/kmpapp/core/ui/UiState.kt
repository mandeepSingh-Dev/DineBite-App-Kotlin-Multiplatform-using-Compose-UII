package com.example.kmpapp.core.ui

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Content<T>(val value: T) : UiState<T>
    data class Error(val message: String, val canRetry: Boolean = true) : UiState<Nothing>
}

inline fun <T> UiState<T>.contentOrNull(): T? = (this as? UiState.Content<T>)?.value
