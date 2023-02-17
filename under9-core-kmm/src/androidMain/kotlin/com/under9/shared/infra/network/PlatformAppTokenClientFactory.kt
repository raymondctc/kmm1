package com.under9.shared.infra.network

import com.under9.shared.infra.network.model.MainApiHeader
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import okhttp3.Interceptor

actual class PlatformAppTokenClientFactory {

    actual fun createClient(
        httpHeaderValueManager: HttpHeaderValueManager
    ): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                val json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    explicitNulls = false
                    isLenient = true
                }
                json(json = json)
//                serializer = KotlinxSerializer(json)
            }
            install(UserAgent) {
                agent = httpHeaderValueManager.createRequestHeader().userAgent
            }
            engine {
                addInterceptor(Interceptor { chain ->
                    val header = httpHeaderValueManager.createRequestHeader()
                    val builder = chain.request().newBuilder()
                    with(header) {
                        builder.addHeader(MainApiHeader.HEADER_TIMESTAMP, timestamp)
                            .addHeader(MainApiHeader.HEADER_TIMESTAMP, timestamp)
                            .addHeader(MainApiHeader.HEADER_APP_ID, packageName)
                            .addHeader(MainApiHeader.HEADER_DEVICE_UUID, deviceUUID)
                            .addHeader(MainApiHeader.HEADER_REQUEST_SIGNATURE, requestSignature)
                            .addHeader(MainApiHeader.HEADER_X_PACKAGE_ID, packageName)
                            .addHeader(MainApiHeader.HEADER_X_PACKAGE_VERSION, packageVersion)
                            .addHeader(MainApiHeader.HEADER_X_PACKAGE_DEVICE_ID, deviceUUID)
                            .addHeader(MainApiHeader.HEADER_DEVICE_TYPE, deviceType)
                        chain.proceed(builder.build())
                    }
                })
            }
        }
    }
}