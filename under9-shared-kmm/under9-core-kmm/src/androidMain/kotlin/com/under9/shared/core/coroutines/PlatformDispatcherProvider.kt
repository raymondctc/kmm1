package com.under9.shared.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class PlatformDispatcherProvider {
    actual fun getMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    actual fun getIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    actual fun getBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Default

    actual fun getUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}