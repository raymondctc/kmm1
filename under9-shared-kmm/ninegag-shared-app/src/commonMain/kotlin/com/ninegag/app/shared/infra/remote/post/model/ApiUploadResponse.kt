package com.ninegag.app.shared.infra.remote.post.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiUploadResponse(
    @JvmField val data: Data
): ApiBaseResponse() {

    @Serializable
    data class Data(
        @JvmField val imageStatus: Int = 0,
        @JvmField val mediaStatus: Int = 0,
        @JvmField val metaStatus: Int = 0,
        @JvmField val urlInfos: Map<String, UrlInfoObject>? = null,
        @JvmField val entryId: String? = null,
        //        post response not implemented for KMM
    )
}

@Serializable
class UrlInfoObject {
    @JvmField val platform: String? = null
    @JvmField val title: String? = null
    @JvmField val videos: List<ApiGagMedia>? = null
    @JvmField val images: List<ApiGagMedia>? = null
}