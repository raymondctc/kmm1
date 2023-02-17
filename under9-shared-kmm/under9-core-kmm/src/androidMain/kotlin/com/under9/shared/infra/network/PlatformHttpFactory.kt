package com.under9.shared.infra.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import okhttp3.Interceptor
import okhttp3.Request
import java.io.IOException
import com.under9.shared.infra.network.model.MainApiHeader;
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

actual class PlatformHttpFactory {
    private lateinit var tokenValueManager: TokenValueManager
    private lateinit var httpHeaderValueManager: HttpHeaderValueManager

    actual fun createClient(
        tokenValueManager: TokenValueManager,
        httpHeaderValueManager: HttpHeaderValueManager
    ): HttpClient {
        this.tokenValueManager = tokenValueManager
        this.httpHeaderValueManager = httpHeaderValueManager

        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                val json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    explicitNulls = false
                    isLenient = true
                    explicitNulls = false
                }
                json(json = json)
            }
            install(Auth) {
                customAppAuth {
                    loadTokens { tokenValueManager.loadTokens() }
                    refreshTokens { tokenValueManager.loadTokens() }
                }
            }
            install(UserAgent) {
                agent = httpHeaderValueManager.createRequestHeader().userAgent
            }
            engine {
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
                .addHeader(MainApiHeader.HEADER_TOKEN, token)
                .addHeader(MainApiHeader.HEADER_TIMESTAMP, timestamp)
                .addHeader(MainApiHeader.HEADER_APP_ID, packageName)
                .addHeader(MainApiHeader.HEADER_DEVICE_UUID, deviceUUID)
                .addHeader(MainApiHeader.HEADER_REQUEST_SIGNATURE, requestSignature)
                .addHeader(MainApiHeader.HEADER_X_PACKAGE_ID, packageName)
                .addHeader(MainApiHeader.HEADER_X_PACKAGE_VERSION, packageVersion)
                .addHeader(MainApiHeader.HEADER_X_PACKAGE_DEVICE_ID, deviceUUID)
                .addHeader(MainApiHeader.HEADER_DEVICE_TYPE, deviceType)
        }

        return builder.build()
    }
}