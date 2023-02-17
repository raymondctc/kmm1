package com.ninegag.app.shared.data.tag

import com.ninegag.app.shared.infra.remote.tag.model.ApiNavListResponse
import com.ninegag.app.shared.infra.remote.tag.model.ApiRelatedTagResponse
import com.ninegag.app.shared.util.runWithSafeApiResult
import com.under9.shared.core.result.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface RemoteTagDataSource {
    suspend fun getNavTagList() : Result<ApiNavListResponse>
    suspend fun getRelatedTagList(tag: String): Result<ApiRelatedTagResponse>
}

class RemoteTagDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteTagDataSource {

    override suspend fun getNavTagList() : Result<ApiNavListResponse> {
        return runWithSafeApiResult {
            httpClient.get("nav-list").body()
        }
    }

    override suspend fun getRelatedTagList(tag: String): Result<ApiRelatedTagResponse> {
        return runWithSafeApiResult {
            httpClient.get("related-tags") {
                parameter("tag", tag)
            }.body()
        }
    }
}
