package com.under9.shared.infra.network

import co.touchlab.stately.isolate.IsolateState
import io.ktor.client.*

expect class PlatformHttpFactory {
    fun createClient(
        tokenValueManager: TokenValueManager,
        httpHeaderValueManager: HttpHeaderValueManager
    ): HttpClient
}