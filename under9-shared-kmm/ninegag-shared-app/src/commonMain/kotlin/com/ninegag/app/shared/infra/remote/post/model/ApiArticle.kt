package com.ninegag.app.shared.infra.remote.post.model

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
data class ApiArticle (
    @JvmField val blocks: List<Block>? = null,
    @JvmField val medias: Map<String, Media>? = null) {

    @Serializable
    data class Block(
        @JvmField val type: String? = null,
        @JvmField val content: String? = null,
        @JvmField val mediaId: String? = null,
        @JvmField val caption: String? = null)

    @Serializable
    data class Media(
        @JvmField val images: ApiGagMediaGroup? = null,
        @JvmField val video: ApiGag.PostVideo? = null,
        @JvmField val id: String? = null,
        @JvmField val hasImageTile: Int = 0,
        @JvmField val type: String? = null,
        @JvmField val postTile: ApiGagTileGroup? = null)
}