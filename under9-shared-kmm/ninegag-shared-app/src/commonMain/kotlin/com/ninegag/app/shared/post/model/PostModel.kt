package com.ninegag.app.shared.post.model

import com.ninegag.app.shared.infra.remote.post.model.*
import com.ninegag.app.shared.infra.remote.tag.model.ApiTag
import com.ninegag.app.shared.infra.remote.user.model.ApiUser
import kotlinx.serialization.json.JsonElement

data class PostModel(
    val postId: String? = null,
    val title: String? = null,
    val type: String? = null,
    val description: String? = null,
    val commentOpClientId: String? = null,
    val commentOpSignature: String? = null,
    val commentsCount: Int? = null,
    val upVoteCount: Int? = null,
    val downVoteCount: Int? = null,
    val nsfw: Int? = null,
    val version: Int? = null,
    val hasLongPostCover: Int? = null,
    val hasImageTile: Int? = null,
    val userScore: Int? = null,
    val albumWebUrl: String? = null,
    val sourceDomain: String? = null,
    val sourceUrl: String? = null,
    val isVoteMasked: Int? = null,
    val creationTs: Long? = null,
    val postSection: ApiPostSection? = null,
    val media: ApiGagMediaGroup? = null,
    val gagTile: ApiGagTileGroup? = null,
    val creator: ApiUser? = null,
    val targetedAdTags: JsonElement? = null,
    val url: String? = null,
    val isAnonymous: Boolean? = null,
    val postVideo: ApiGag.PostVideo? = null, // Null when videoSource is Youtube
    val article: ApiArticle? = null,
    val tags: List<ApiTag>? = null,
    val comment: ApiGag.Comment? = null,
    val orderId: Long = 0,
    val promoted: Int = 0,
    val postUser: ApiGag.PostUser? = null
)
