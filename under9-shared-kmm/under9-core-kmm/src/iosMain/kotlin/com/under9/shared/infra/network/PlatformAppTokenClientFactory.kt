package com.under9.shared.infra.network

import com.under9.shared.infra.network.model.MainApiHeader
import io.github.aakira.napier.Napier
import io.ktor.client.*
import platform.Foundation.allHTTPHeaderFields
import platform.Foundation.setValue

actual class PlatformAppTokenClientFactory {

    actual fun createClient(httpHeaderValueManager: HttpHeaderValueManager): HttpClient {
        TODO("Not implemented")
//        return HttpClient(Ios) {
//            install(JsonFeature) {
//                val json = kotlinx.serialization.json.Json {
//                    ignoreUnknownKeys = true
//                    useAlternativeNames = false
//                    explicitNulls = false
//                    isLenient = true
//                    explicitNulls = false
//                }
//                serializer = KotlinxSerializer(json)
//            }
//            install(UserAgent) {
//                agent = httpHeaderValueManager.createRequestHeader().userAgent
//            }
//            engine {
//                configureRequest {
//                    val headerRequestDataModel = httpHeaderValueManager.createRequestHeader()
//
//                    with(headerRequestDataModel) {
//                        setValue(timestamp, MainApiHeader.HEADER_TIMESTAMP)
//                        setValue(packageName, MainApiHeader.HEADER_APP_ID)
//                        setValue(deviceUUID, MainApiHeader.HEADER_DEVICE_UUID)
//                        setValue(requestSignature, MainApiHeader.HEADER_REQUEST_SIGNATURE)
//                        setValue(packageName, MainApiHeader.HEADER_X_PACKAGE_ID)
//                        setValue(packageVersion, MainApiHeader.HEADER_X_PACKAGE_VERSION)
//                        setValue(deviceUUID, MainApiHeader.HEADER_X_PACKAGE_DEVICE_ID)
//                        setValue(deviceType, MainApiHeader.HEADER_DEVICE_TYPE)
//                    }
//
//                    Napier.d("@@@ PlatformAppTokenClientFactory=${this.allHTTPHeaderFields}")
//                }
//            }
//        }
    }
}