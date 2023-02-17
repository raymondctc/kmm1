package com.ninegag.app.shared.infra.remote.post.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiGagMedia(
    @JvmField val width: Int = 0,
    @JvmField val height: Int = 0,
    @JvmField val url: String? = null,
    @JvmField val webpUrl: String? = null,
    @JvmField val vp9Url: String? = null,
    @JvmField val hasAudio: Int? = null,
    @JvmField val duration: Long? = null,
    @JvmField val vp8Url: String? = null
)
