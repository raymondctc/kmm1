package com.ninegag.app.shared.infra.remote.post.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiGagMediaGroup (
    @JvmField val image460: ApiGagMedia? = null,
    @JvmField val image700: ApiGagMedia? = null,
    @JvmField val image460sa: ApiGagMedia? = null,
    @JvmField val image700ba: ApiGagMedia? = null,
    @JvmField val image460c: ApiGagMedia? = null,
    @JvmField val imageFbThumbnail: ApiGagMedia? = null,
    @JvmField val imageXLarge: ApiGagMedia? = null,
    @JvmField val image460sv: ApiGagMedia? = null)
