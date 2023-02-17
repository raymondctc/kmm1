package com.under9.shared.infra.network

import com.under9.shared.infra.db.*
import com.under9.shared.infra.db.getString
import com.under9.shared.infra.db.putString
import io.ktor.client.*

abstract class TokenValueManager(
    private val preferences: Preferences,
    private val tokenPrefKey: String,
    private val tokenExpirePrefKey: String,
    private val secondsTillExpiryPrefKey: String,
    val tokenClient: HttpClient
) {

    fun getToken(): String {
        return preferences.getString(tokenPrefKey)
    }

    fun setToken(token: String) {
        preferences.putString(tokenPrefKey, token)
    }

    fun setTokenExpiryTs(value: Long) {
        preferences.putLong(tokenExpirePrefKey, value)
    }

    fun getTokenExpiryTs(): Long {
        return preferences.getLong(tokenExpirePrefKey)
    }

    fun setSecondsTillExpiry(secondsTillExpiry: Long) {
        preferences.putLong(secondsTillExpiryPrefKey, secondsTillExpiry)
    }

    fun getSecondsTillExpiry(): Long {
        return preferences.getLong(secondsTillExpiryPrefKey)
    }

    abstract suspend fun loadTokens(): AppAccessTokenInfo?
}