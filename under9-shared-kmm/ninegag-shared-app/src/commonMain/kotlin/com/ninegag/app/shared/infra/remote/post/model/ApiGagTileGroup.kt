package com.ninegag.app.shared.infra.remote.post.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiGagTileGroup (@JvmField val h800: ApiGagTile? = null)

@Serializable
data class ApiGagTile (
    @JvmField val width: Int = 0,
    @JvmField val height: Int = 0,
    @JvmField val images: List<ApiGagTileImage>? = null)

@Serializable
data class ApiGagTileImage(
    @JvmField val width: Int = 0,
    @JvmField val height: Int = 0,
    @JvmField val url: String? = null,
    @JvmField val webpUrl: String? = null)