package com.under9.shared.infra.network

import com.under9.shared.infra.network.model.MainApiHeader
import io.ktor.client.plugins.auth.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.auth.*

fun Auth.customAppAuth(block: AppAuthCustomConfig.() -> Unit) {
    with(AppAuthCustomConfig().apply(block)) {
        providers.add(AppCustomAuthProvider(_refreshTokens, _loadTokens, _sendWithoutRequest))
    }
}

class AppAuthCustomConfig {
    internal var _refreshTokens: suspend (response: HttpResponse) -> AppAccessTokenInfo? = { null }
    internal var _loadTokens: suspend () -> AppAccessTokenInfo? = { null }
    internal var _sendWithoutRequest: (HttpRequestBuilder) -> Boolean = { true }

    fun refreshTokens(block: suspend (response: HttpResponse) -> AppAccessTokenInfo?) {
        _refreshTokens = block
    }

    fun loadTokens(block: suspend () -> AppAccessTokenInfo?) {
        _loadTokens = block
    }
}

data class AppAccessTokenInfo(
    val accessToken: String,
    val expiryTs: Long,
    val secondsTillExpiry: Long
)

class AppCustomAuthProvider(
    private val refreshTokens: suspend (response: HttpResponse) -> AppAccessTokenInfo?,
    loadTokens: suspend () -> AppAccessTokenInfo?,
    private val sendWithoutRequestCallback: (HttpRequestBuilder) -> Boolean = { true }
) : AuthProvider {

    private val tokensHolder = AppAuthTokenHolder(loadTokens)
    override val sendWithoutRequest: Boolean
        get() = error("Deprecate")

    override fun sendWithoutRequest(request: HttpRequestBuilder): Boolean = sendWithoutRequestCallback(request)

    override suspend fun addRequestHeaders(request: HttpRequestBuilder, authHeader: HttpAuthHeader?) {
        val token = tokensHolder.loadToken() ?: return

        request.headers {
            val tokenValue = token.accessToken
            if (contains(MainApiHeader.HEADER_TOKEN)) {
                remove(MainApiHeader.HEADER_TOKEN)
            }
            append(MainApiHeader.HEADER_TOKEN, tokenValue)
        }
    }

    override suspend fun refreshToken(response: HttpResponse): Boolean {
        return tokensHolder.setToken { refreshTokens(response) } != null
    }


    override fun isApplicable(auth: HttpAuthHeader): Boolean {
        return true
    }

    suspend fun clearToken() {
        tokensHolder.clearToken()
    }
}