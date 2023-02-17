package com.under9.shared.core.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

actual class AppMainScope actual constructor(
    actual val platformDispatcherProvider: PlatformDispatcherProvider
): CoroutineScope {
    private val dispatcher = platformDispatcherProvider.getMainDispatcher()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}