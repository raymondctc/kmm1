package com.ninegag.app.shared.data.post

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.infra.remote.post.model.ApiPostsResponse
import com.ninegag.app.shared.infra.remote.post.model.ApiUploadResponse
import com.ninegag.app.shared.util.checkAndReturnResult
import com.ninegag.app.shared.util.runWithSafeApiResult
import io.github.aakira.napier.Napier
import com.under9.shared.core.result.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

interface RemotePostDatasource {
    suspend fun deletePost(postId: String): Result<ApiBaseResponse>
    suspend fun submitPostWithLink(uuid: String, url: String): Result<ApiUploadResponse>
    suspend fun getRemoteRelatedPosts(postId: String, after: String?): Result<ApiPostsResponse>
}

internal class RemotePostDatasourceImpl(private val httpClient: HttpClient): RemotePostDatasource {

    override suspend fun deletePost(postId: String): Result<ApiBaseResponse> {
        return try {
            val response = httpClient.delete {
                url {
                    encodedPath = "post"
                    parameter("entryId", postId)
                }
            }.body<ApiBaseResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error", e)
            Result.Error(e)
        }
    }

    override suspend fun submitPostWithLink(uuid: String, url: String): Result<ApiUploadResponse> {
        return try {
            val response = httpClient.submitForm(
                url = "post-submit/step/urlData",
                formParameters = Parameters.build {
                    append("uploadId", uuid)
                    append("urlMedia", url)
                }
            ).body<ApiUploadResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error", e)
            Result.Error(e)
        }
    }

    override suspend fun getRemoteRelatedPosts(postId: String, after: String?): Result<ApiPostsResponse> {
        return runWithSafeApiResult {
            val request = httpClient.get("related-posts") {
                parameter("id", postId)
                if (!after.isNullOrBlank()) parameter("after", after)
            }
            request.body()
        }
    }
}