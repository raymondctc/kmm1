package com.ninegag.app.shared.infra.remote

import com.ninegag.app.shared.AppContext
import com.ninegag.app.shared.config.UserAgentConfig
import com.ninegag.app.shared.domain.GagHttpHeaderValueManager
import io.ktor.client.engine.*

expect class GagHttpEngineFactory(
    httpHeaderValueManager: GagHttpHeaderValueManager,
    userAgentConfig: UserAgentConfig,
    appContext: AppContext<*>,
) {
    fun createHttpEngine(): HttpClientEngineFactory<HttpClientEngineConfig>

    fun setEngineConfig(config: HttpClientEngineConfig)
}