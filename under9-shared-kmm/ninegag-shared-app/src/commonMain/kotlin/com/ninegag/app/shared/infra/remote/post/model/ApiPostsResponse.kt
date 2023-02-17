package com.ninegag.app.shared.infra.remote.post.model

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.infra.remote.tag.model.ApiFeaturedAds
import com.ninegag.app.shared.infra.remote.tag.model.ApiTag
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.jvm.JvmField

@Serializable
data class ApiPostsResponse(@JvmField val data: Data): ApiBaseResponse() {

    @Serializable
    data class Data(
        @JvmField val didEndOfList: Int = 0,
        @JvmField val tag: ApiTag? = null,

        @JvmField val posts: List<ApiGag>? = null,
        @JvmField val targetedAdTags: JsonElement? = null,

        @JvmField val featuredAds: List<ApiFeaturedAds>? = null,
        @JvmField val nextRefKey: String? = null,
        @JvmField val prevRefKey: String? = null,
        @JvmField val relatedTags: List<ApiTag>? = null,

        @JvmField val after: String? = null,
        @JvmField val feedId: String? = null,
    )
}