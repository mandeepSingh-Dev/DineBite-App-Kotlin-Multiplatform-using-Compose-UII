package com.example.kmpapp.core.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

public val sharedJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
}

/**
 * Extension function to parse a JSON string into an object of type [T] securely.
 */
inline fun <reified T> String.fromJson(): T? {
    return try {
        sharedJson.decodeFromString<T>(this)
    } catch (e: Exception) {
        println("JSON_PARSE_ERROR: ${e.message}")
        null
    }
}

/**
 * Extension function to parse a JSON string into a list of [T] securely.
 */
inline fun <reified T> String.fromJsonArray(): List<T>? {
    return try {
        sharedJson.decodeFromString<List<T>>(this)
    } catch (e: Exception) {
        println("JSON_PARSE_ERROR: ${e.message}")
        null
    }
}

/**
 * Extension function to serialize any object into a JSON string securely.
 */
inline fun <reified T> T.toJson(): String? {
    return try {
        sharedJson.encodeToString(this)
    } catch (e: Exception) {
        println("JSON_SERIALIZE_ERROR: ${e.message}")
        null
    }
}
