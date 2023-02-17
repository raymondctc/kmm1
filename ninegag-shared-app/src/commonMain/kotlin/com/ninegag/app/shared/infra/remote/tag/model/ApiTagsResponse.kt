package com.ninegag.app.shared.infra.remote.tag.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import kotlin.jvm.JvmField

class ApiTagsResponse(
    @JvmField val data: Data
) : ApiBaseResponse() {
    data class Data(
        @JvmField val tags: List<ApiTag>
    )
}