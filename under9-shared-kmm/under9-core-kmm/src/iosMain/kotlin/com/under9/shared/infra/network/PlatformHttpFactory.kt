package com.under9.shared.infra.network

import com.under9.shared.infra.network.model.MainApiHeader
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.ios.*
import platform.Foundation.allHTTPHeaderFields
import platform.Foundation.setValue

actual class PlatformHttpFactory {
    private lateinit var tokenValueManager: TokenValueManager
    private lateinit var httpHeaderValueManager: HttpHeaderValueManager

    actual fun createClient(
        tokenValueManager: TokenValueManager,
        httpHeaderValueManager: HttpHeaderValueManager
    ): HttpClient {
        TODO()
//        this.tokenValueManager = tokenValueManager
//        this.httpHeaderValueManager = httpHeaderValueManager
//
//        return HttpClient(Ios) {
//            // https://stackoverflow.com/questions/67969562/kotlin-native-concurrent-invalidmutabilityexception-mutation-attempt-of-frozen
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
//            install(Auth) {
//                customAppAuth {
//                    loadTokens { tokenValueManager.loadTokens() }
//                    refreshTokens { tokenValueManager.loadTokens() }
//
//                }
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
//                    Napier.d("@@@ headers=${this.allHTTPHeaderFields}")
//                }
//            }
//        }
    }
}