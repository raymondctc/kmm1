package com.under9.shared.core.util

actual object PlatformUtils {

    actual val platform = "Android"

    actual val currentTimeMillis: Long
        get() {
            return System.currentTimeMillis()
        }

    actual val currentThreadInfo: String
        get() {
            return Thread.currentThread().toString()
        }

}