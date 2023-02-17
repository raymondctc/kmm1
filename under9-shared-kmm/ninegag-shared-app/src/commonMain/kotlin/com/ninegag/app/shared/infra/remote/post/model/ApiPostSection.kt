package com.ninegag.app.shared.infra.remote.post.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
class ApiPostSection(
    @JvmField var name: String,
    @JvmField var url: String,
    @JvmField var imageUrl: String,
    @JvmField var webpUrl: String?,
    @JvmField val sectionTag: String?
) {
    fun getImage(): String {
        return webpUrl ?: imageUrl
    }
}