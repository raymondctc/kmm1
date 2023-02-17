package com.ninegag.app.shared.infra.remote.section.model

import com.ninegag.app.shared.infra.remote.tag.model.ApiTag
import kotlin.jvm.JvmField

data class ApiSection(
    @JvmField val id: String,
    @JvmField val url: String,
    @JvmField val name: String,
    @JvmField val ogImageUrl: String,
    @JvmField val ogWebpUrl: String,
    @JvmField val description: String,
    @JvmField val sectionTag: ApiTag?,
    @JvmField val userUploadEnabled: Int = 0,
    @JvmField val isSensitive: Int = 0,
    @JvmField val featuredTags: List<ApiTag>,
    @JvmField val listTypes: List<String>
)