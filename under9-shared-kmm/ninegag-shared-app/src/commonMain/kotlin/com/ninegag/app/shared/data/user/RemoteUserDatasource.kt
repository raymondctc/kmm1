package com.ninegag.app.shared.data.user

import com.ninegag.app.shared.data.user.RemoteUserDatasource.Companion.VALUE_COMMENT
import com.ninegag.app.shared.data.user.RemoteUserDatasource.Companion.VALUE_POST
import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.util.checkAndReturnResult
import com.russhwolf.settings.Settings
import com.under9.shared.core.U9Json
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import com.under9.shared.core.result.Result
import io.ktor.client.request.*

interface RemoteUserDatasource {
    companion object {
        const val VALUE_POST = "post"
        const val VALUE_COMMENT = "comment"
    }

    suspend fun blockPost(postId: String, type: BlockPostType): Result<ApiBaseResponse>
    suspend fun blockUser(accountId: String): Result<ApiBaseResponse>
    suspend fun unblockUser(accountId: String): Result<ApiBaseResponse>

    enum class BlockPostType {
        POST, COMMENT
    }

}

internal class RemoteUserDatasourceImpl(private val httpClient: HttpClient): RemoteUserDatasource {

    override suspend fun blockPost(postId: String, type: RemoteUserDatasource.BlockPostType): Result<ApiBaseResponse> {
        return try {
            Napier.d("blockPost api call")
            val response = httpClient.submitForm(
                url = "block-post",
                formParameters = Parameters.build {
                    append("id", postId)
                    append("type", when(type) {
                        RemoteUserDatasource.BlockPostType.POST -> VALUE_POST
                        RemoteUserDatasource.BlockPostType.COMMENT -> VALUE_COMMENT
                    })
                }
            ).body<ApiBaseResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error", e)
            Result.Error(e)
        }
    }

    override suspend fun blockUser(accountId: String): Result<ApiBaseResponse> {
        return try {
            Napier.d("blockPost api call")
            val response = httpClient.submitForm(
                url = "user-block/accountId",
                formParameters = Parameters.build {
                    append("accountId", accountId)
                }
            ).body<ApiBaseResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error", e)
            Result.Error(e)
        }
    }

    override suspend fun unblockUser(accountId: String): Result<ApiBaseResponse> {
        return try {
            val response = httpClient.delete("user-block/accountId") {
                parameter("accountId", accountId)
            }.body<ApiBaseResponse>()
            checkAndReturnResult(response)
        } catch (e: Exception) {
            Napier.e("error", e)
            Result.Error(e)
        }
    }
}
