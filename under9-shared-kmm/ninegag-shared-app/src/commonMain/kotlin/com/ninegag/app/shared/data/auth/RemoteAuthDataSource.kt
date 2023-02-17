package com.ninegag.app.shared.data.auth

import com.ninegag.app.shared.infra.remote.auth.model.ApiAuthResponse
import com.ninegag.app.shared.util.runWithSafeApiResult
import com.under9.shared.core.result.Result
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

interface RemoteAuthDataSource {
    suspend fun loginGuest(): Result<ApiAuthResponse>
    suspend fun loginBy9Gag(loginName: String, password: String): Result<ApiAuthResponse>
}

class RemoteAuthDataSourceImpl(
    private val httpClient: HttpClient,
) : RemoteAuthDataSource {

    override suspend fun loginGuest(): Result<ApiAuthResponse> {
        return runWithSafeApiResult {
            httpClient.get("guest-token").body()
        }
    }

    override suspend fun loginBy9Gag(loginName: String, password: String): Result<ApiAuthResponse> {
        Napier.d("loginName=$loginName, password=$password")
        return runWithSafeApiResult {
            httpClient.submitForm(
                url = "user-token",
                formParameters = Parameters.build {
                    append("loginMethod", "9gag")
                    append("loginName", loginName)
                    append("password", password)
                }
            ).body()
        }
    }
}

