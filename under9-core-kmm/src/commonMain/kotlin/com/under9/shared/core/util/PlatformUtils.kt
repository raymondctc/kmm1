package com.under9.shared.core.util

expect object PlatformUtils {
    val platform: String

    val currentTimeMillis: Long

    val currentThreadInfo: String
}