package com.ninegag.app.shared.infra.remote.section.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.infra.remote.interest.model.ApiInterest
import com.ninegag.app.shared.infra.remote.tag.model.ApiTag
import kotlin.jvm.JvmField

data class ApiSectionsResponse(
    @JvmField val data: Data
) : ApiBaseResponse() {
    data class Data(
        @JvmField val groups: List<ApiSection>,
        @JvmField val locals: List<ApiSection>?,
        @JvmField val featured: List<ApiSection>?,
        @JvmField val hot: Hot?,
        @JvmField val interests: List<ApiInterest>?
    )

    data class Hot(
        @JvmField val featuredTags: List<ApiTag>
    )
}