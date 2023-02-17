package com.ninegag.app.shared.infra.remote

import com.ninegag.app.shared.AppContext
import com.ninegag.app.shared.config.UserAgentConfig
import com.ninegag.app.shared.domain.GagHttpHeaderValueManager
import com.under9.shared.infra.network.model.MainApiHeader
import io.github.aakira.napier.Napier
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import platform.Foundation.setValue

actual class GagHttpEngineFactory actual constructor(
    private val httpHeaderValueManager: GagHttpHeaderValueManager,
    private val userAgentConfig: UserAgentConfig,
    private val appContext: AppContext<*>
) {

    actual fun createHttpEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = Darwin
    actual fun setEngineConfig(config: HttpClientEngineConfig) {
        (config as DarwinClientEngineConfig).apply {
            configureRequest {
                val headerRequestDataModel = httpHeaderValueManager.createRequestHeader()
                with(headerRequestDataModel) {
                    setValue(timestamp, MainApiHeader.HEADER_TIMESTAMP)
                    setValue(packageName, MainApiHeader.HEADER_APP_ID)
                    setValue(deviceUUID, MainApiHeader.HEADER_DEVICE_UUID)
                    setValue(requestSignature, MainApiHeader.HEADER_REQUEST_SIGNATURE)
                    setValue(packageName, MainApiHeader.HEADER_X_PACKAGE_ID)
                    setValue(packageVersion, MainApiHeader.HEADER_X_PACKAGE_VERSION)
                    setValue(deviceUUID, MainApiHeader.HEADER_X_PACKAGE_DEVICE_ID)
                    setValue(deviceType, MainApiHeader.HEADER_DEVICE_TYPE)
                    if (httpHeaderValueManager.getToken().isNotEmpty()) {
                        setValue(httpHeaderValueManager.getToken(), MainApiHeader.HEADER_TOKEN)
                    }
                }
                Napier.d("headerRequestDataModel=$headerRequestDataModel")
                Napier.d("userToken=${httpHeaderValueManager.getToken()}")
            }
        }
    }
}