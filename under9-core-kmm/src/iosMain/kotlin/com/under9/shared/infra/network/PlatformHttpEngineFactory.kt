package com.under9.shared.infra.network

import com.under9.shared.infra.network.model.MainApiHeader
import io.github.aakira.napier.Napier
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import platform.Foundation.setValue

actual class PlatformHttpEngineFactory actual constructor(
    private val tokenValueManager: TokenValueManager,
    private val httpHeaderValueManager: HttpHeaderValueManager
) {
    actual fun createHttpEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = Darwin

    actual fun createEngineConfig(): HttpClientEngineConfig {
        return DarwinClientEngineConfig().apply {
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
                }
            }
        }
    }
}