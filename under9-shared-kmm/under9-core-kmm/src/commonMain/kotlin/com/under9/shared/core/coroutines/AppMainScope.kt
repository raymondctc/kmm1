package com.under9.shared.core.coroutines

import kotlinx.coroutines.CoroutineScope

expect class AppMainScope(
    platformDispatcherProvider: PlatformDispatcherProvider
): CoroutineScope {
    val platformDispatcherProvider: PlatformDispatcherProvider
}