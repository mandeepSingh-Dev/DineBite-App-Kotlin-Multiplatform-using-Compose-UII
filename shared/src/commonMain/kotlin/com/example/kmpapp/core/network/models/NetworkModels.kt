package com.example.kmpapp.core.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse2(
    val status: Int? = null,
    val success: Boolean? = null,
    val message: String? = null
)

@Serializable
data class ErrorResponse(
    val status: Int? = null,
    val success: Boolean? = null,
    val message: String? = null,
    val error: String? = null
) {
    val displayMessage: String?
        get() = message ?: error
}
