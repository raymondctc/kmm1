package com.under9.shared.core.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

actual class AppMainScope actual constructor(
    actual val platformDispatcherProvider: PlatformDispatcherProvider
): CoroutineScope {
    var viewModel: ViewModel? = null

    override val coroutineContext: CoroutineContext
        get() = viewModel!!.viewModelScope.coroutineContext
}