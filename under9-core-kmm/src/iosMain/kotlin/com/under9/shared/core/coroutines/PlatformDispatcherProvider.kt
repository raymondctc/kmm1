package com.under9.shared.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class PlatformDispatcherProvider {

    actual fun getMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    // Ref: https://github.com/Kotlin/kotlinx.coroutines/issues/1889
    // In particular, you cannot use dispatch_get_global_queue(QOS_CLASS_BACKGROUND) to dispatch coroutines in Kotlin/Native
    actual fun getIoDispatcher(): CoroutineDispatcher = Dispatchers.Default

    actual fun getBackgroundDispatcher(): CoroutineDispatcher = Dispatchers.Default

    actual fun getUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}