package com.under9.shared.infra.network

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

class HttpFactory(
    private val tokenValueManager: TokenValueManager,
    private val httpHeaderValueManager: HttpHeaderValueManager,
    private val platformHttpEngineFactory: PlatformHttpEngineFactory
) {
    fun createClient(): HttpClient {
        return HttpClient(platformHttpEngineFactory.createHttpEngine()) {
            install(ContentNegotiation) {
                val json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    explicitNulls = false
                    isLenient = true
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
                platformHttpEngineFactory.createEngineConfig()
            }
        }
    }
}