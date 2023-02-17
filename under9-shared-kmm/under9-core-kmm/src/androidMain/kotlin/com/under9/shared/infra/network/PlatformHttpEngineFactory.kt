package com.under9.shared.infra.network

import io.github.aakira.napier.Napier
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import okhttp3.Interceptor
import okhttp3.Request
import java.io.IOException

actual class PlatformHttpEngineFactory actual constructor(
    private val tokenValueManager: TokenValueManager,
    private val httpHeaderValueManager: HttpHeaderValueManager,
) {
    actual fun createHttpEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
    actual fun createEngineConfig(): HttpClientEngineConfig {
        return OkHttpConfig().apply {
            addInterceptor(createHeaderInterceptor())
            addNetworkInterceptor(Interceptor { chain ->
                val request = chain.request()
                try {
                    return@Interceptor chain.proceed(request)
                } catch (e: IOException) {
                    throw e
                }
            })
        }
    }

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->

            val original = chain.request()
            val request = buildRequest(original)
            Napier.d("Requesting Url: ${request.url}")
//            if (!request.url.toString().contains(TokenExpirationChecker.KEY_API_RENEW)) {
//                runBlocking {
//                    tokenExpirationChecker.check()
//                }
//            }

            Napier.d("After checking token, requesting Url: ${request.url}")

            val response = chain.proceed(request)
            response
        }
    }

    private fun buildRequest(original: Request): Request {
        val token = tokenValueManager.getToken()
        val headerRequestDataModel = httpHeaderValueManager.createRequestHeader()
        Napier.d("Platformhttpfactory token=$token")
        val builder = original.newBuilder()
        with(headerRequestDataModel) {
            builder
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_TOKEN, token)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_TIMESTAMP, timestamp)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_APP_ID, packageName)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_DEVICE_UUID, deviceUUID)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_REQUEST_SIGNATURE, requestSignature)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_X_PACKAGE_ID, packageName)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_X_PACKAGE_VERSION, packageVersion)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_X_PACKAGE_DEVICE_ID, deviceUUID)
                .addHeader(com.under9.shared.infra.network.model.MainApiHeader.HEADER_DEVICE_TYPE, deviceType)
        }

        return builder.build()
    }

}