package com.under9.shared.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

expect class PlatformDispatcherProvider {
    fun getMainDispatcher(): CoroutineDispatcher

    fun getIoDispatcher(): CoroutineDispatcher

    fun getBackgroundDispatcher(): CoroutineDispatcher

    fun getUnconfinedDispatcher(): CoroutineDispatcher
}