package com.under9.shared.chat.util

import com.under9.shared.chat.api.ApiServiceManager
import com.under9.shared.chat.api.ApiServiceManager.chatApiUrl
import com.under9.shared.chat.api.model.ApiLoginResponse
import com.under9.shared.core.util.AuthUtil
import com.under9.shared.infra.db.Preferences
import com.under9.shared.infra.db.getString
import com.under9.shared.infra.db.putString
import com.under9.shared.infra.network.AppAccessTokenInfo
import com.under9.shared.infra.network.PlatformAppTokenClientFactory
import com.under9.shared.infra.network.TokenValueManager
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ChatTokenValueManager(
    private val preferences: Preferences,
    tokenClient: HttpClient
) : TokenValueManager(
    preferences,
    KEY_HEY_USER_API_ACCESS_TOKEN,
    KEY_HEY_USER_API_EXPIRATION_TS_KEY,
    KEY_SECOND_TILL_EXPIRE_KEY,
    tokenClient
) {
    companion object {
        const val KEY_HEY_USER_API_ACCESS_TOKEN = "hey_user_api_access_token"
        const val KEY_HEY_USER_API_EXPIRATION_TS_KEY = "hey_user_api_access_token_expiration_ts"
        const val KEY_SECOND_TILL_EXPIRE_KEY = "hey_user_seconds_till_expiry"
        const val KEY_GAG_HEY_JWT_TOKEN = "gag_hey_jwt_token"
    }

    fun storeHeyJWT(token: String) {
        preferences.putString(KEY_GAG_HEY_JWT_TOKEN, token)
    }

    fun getHeyJWT(): String {
        return preferences.getString(KEY_GAG_HEY_JWT_TOKEN)
    }

    override suspend fun loadTokens(): AppAccessTokenInfo? {
        Napier.d("Should renew token?=${shouldRenewToken()}")

        return if (shouldRenewToken()) {
            try {
                Napier.d("getHeyJWT() token=${getHeyJWT()}")
                val response = tokenClient.submitForm(
                    url = "${ApiServiceManager.API_BASE_URL}authenticate",
                    formParameters = Parameters.build {
                        append("userAccessToken", getHeyJWT())
                    }
                ).body<ApiLoginResponse>()

                val token = response.data!!.userToken
                val expiryTs = response.data.tokenExpiry
                val secondsTillExpiry = response.data.secondsTillExpiry
                setToken(token)
                setTokenExpiryTs(expiryTs)
                setSecondsTillExpiry(secondsTillExpiry)
                Napier.d("Renewed new token token=$token, tokenExpiryTs=$expiryTs, secondsTillExpiry=$secondsTillExpiry")
                AppAccessTokenInfo(
                    accessToken = token,
                    expiryTs = expiryTs,
                    secondsTillExpiry = secondsTillExpiry
                )
            } catch (e: Exception) {
                Napier.e("error=", e)
                null
            }
        } else {
            AppAccessTokenInfo(
                accessToken = getToken(),
                expiryTs = getTokenExpiryTs(),
                secondsTillExpiry = getSecondsTillExpiry()
            )
        }
    }

    private fun shouldRenewToken(): Boolean {
        val expiryTs = getTokenExpiryTs()
        val token = getToken()
        val secondsTillExpiry = getSecondsTillExpiry()
        Napier.d("Token expiryTs=${expiryTs}, token=${token}")

        val shouldRenew = token.isNotBlank() && (AuthUtil.isUserTokenExpired(expiryTs) || AuthUtil.isUserTokenExpiringSoon(expiryTs, secondsTillExpiry))
        return shouldRenew
    }

}