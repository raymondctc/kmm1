package com.ninegag.app.shared.infra.remote.tag.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiRelatedTagResponse(
    @JvmField val data: Data
): ApiBaseResponse() {

    @Serializable
    data class Data(
        @JvmField val tags: List<ApiTag>
    )
}