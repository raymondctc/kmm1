package com.under9.shared.core

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object U9Json {
    val json = Json {
        explicitNulls = false
        ignoreUnknownKeys = true
    }

    inline fun <reified T> decodeFromString(value: String): T {
        val v = json.decodeFromString<T>(value)
        return v
    }

    inline fun <reified T> encodeToString(value: T): String {
        return json.encodeToString(value)
    }
}
