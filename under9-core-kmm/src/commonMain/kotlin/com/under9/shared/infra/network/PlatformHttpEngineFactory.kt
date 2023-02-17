package com.under9.shared.infra.network

import io.ktor.client.engine.*

expect class PlatformHttpEngineFactory(
    tokenValueManager: TokenValueManager,
    httpHeaderValueManager: HttpHeaderValueManager
) {
    fun createHttpEngine(): HttpClientEngineFactory<HttpClientEngineConfig>

    fun createEngineConfig(): HttpClientEngineConfig
}