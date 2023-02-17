package com.ninegag.app.shared.infra.remote.tag.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiTag(
    @JvmField val key: String,
    @JvmField val url: String,
    @JvmField val isSensitive: Int? = null,
    @JvmField val description: String? = null,
    @JvmField val count: Int = 0  // Post count
)
