package com.ninegag.app.shared.data.post

import com.ninegag.app.shared.infra.remote.post.model.ApiUploadResponse
import com.under9.shared.core.result.Result

interface PostFacade {
    suspend fun submitPostWithLink(uuid: String, url: String): Result<ApiUploadResponse>
}

class PostFacadeImpl(
    private val remotePostDatasource: RemotePostDatasource
): PostFacade {
    override suspend fun submitPostWithLink(uuid: String, url: String): Result<ApiUploadResponse> {
        return remotePostDatasource.submitPostWithLink(uuid, url)
    }
}