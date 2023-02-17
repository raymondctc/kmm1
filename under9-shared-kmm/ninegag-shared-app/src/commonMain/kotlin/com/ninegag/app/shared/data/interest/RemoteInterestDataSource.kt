package com.ninegag.app.shared.data.interest

import com.ninegag.app.shared.infra.remote.tag.model.ApiNavListResponse
import com.ninegag.app.shared.util.runWithSafeApiResult
import com.under9.shared.core.result.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface RemoteInterestDataSource {
    suspend fun getInterestList() : Result<ApiNavListResponse>
}

class RemoteInterestDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteInterestDataSource {

    override suspend fun getInterestList() : Result<ApiNavListResponse> {
        return runWithSafeApiResult {
            val call = httpClient.get("nav-list")
            call.body()
        }
    }
}