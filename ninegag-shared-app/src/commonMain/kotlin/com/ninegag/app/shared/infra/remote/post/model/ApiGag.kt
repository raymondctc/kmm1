package com.ninegag.app.shared.infra.remote.post.model

import com.ninegag.app.shared.infra.remote.tag.model.ApiTag
import com.ninegag.app.shared.infra.remote.user.model.ApiUser
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.jvm.JvmField

@Serializable
data class ApiGag(
    @JvmField val id: String? = null,
    @JvmField val title: String? = null,
    @JvmField val description: String? = null,
    @JvmField val type: String? = null,
    @JvmField val channel: String? = null,
    @JvmField val status: String? = null,

    @JvmField val commentSystem: String? = null,
    @JvmField val created: String? = null,

    @JvmField val userScore: Int = 0,
    @JvmField val score: Int = 0,
    @JvmField val reportedStatus: Int = 0,
    @JvmField val commentsCount: Int = 0,
    @JvmField val fbShares: Int = 0,
    @JvmField val tweetCount: Int = 0,
    @JvmField val downVoteCount: Int = 0,
    @JvmField val upVoteCount: Int = 0,
    @JvmField val totalVoteCount: Int = 0,
    @JvmField val viewsCount: Int = 0,
    @JvmField val version: Int = 0,
    @JvmField val nsfw: Int = 0,
    @JvmField val hasLongPostCover: Int = 0,
    @JvmField val hasImageTile: Int = 0,
    @JvmField val promoted: Int = 0,
    @JvmField val isVoteMasked: Int = 0,
    @JvmField val orderId: Long = 0,
    @JvmField val sortTs: Long = 0,
    @JvmField val creationTs: Long = 0,
    @JvmField val colors: JsonElement? = null,
    @JvmField val isVoted: String? = null,

    @JvmField val commentOpClientId: String? = null,
    @JvmField val commentOpSignature: String? = null,

    @JvmField val imageUrlVideoPreview: String? = null,
    @JvmField val videoSource: String? = null,
    @JvmField val videoId: String? = null,
    @JvmField val postVideo: PostVideo? = null, // Null when videoSource is Youtube
    @JvmField val postUser: PostUser? = null,

    @JvmField val featuredImageUrl: String? = null,

    @JvmField val albumWebUrl: String? = null,

    @JvmField val sourceDomain: String? = null,
    @JvmField val sourceUrl: String? = null,
    @JvmField val url: String? = null,
    @JvmField val externalUrl: String? = null,

    @JvmField val creator: ApiUser? = null,
    @JvmField val images: ApiGagMediaGroup? = null,
    @JvmField val postTile: ApiGagTileGroup? = null,

    @JvmField val postSection: ApiPostSection? = null,
    @JvmField val targetedAdTags: JsonElement? = null,
    @JvmField val tags: List<ApiTag>? = null,

    @JvmField val article: ApiArticle? = null,
    @JvmField val comment: Comment? = null,
    @JvmField val isAnonymous: Boolean = false) {

    companion object {
        const val TAG = "ApiGag_KMM"

        const val TYPE_PHOTO = "Photo"
        const val TYPE_VIDEO = "Video"
        const val TYPE_ANIMATED = "Animated"
        const val TYPE_ARTICLE = "Article"
        const val TYPE_TEXT = "Text"
    }

    @Serializable
    data class PostUser (
        @JvmField val actionsText: String? = null,
        @JvmField val commentId: String? = null)

    @Serializable
    data class PostVideo (
        @JvmField val id: String? = null,
        @JvmField val source: String? = null,
        @JvmField val startTs: Long = 0,
        @JvmField val endTs: Long = 0,
        @JvmField val duration: Long = 0)

    @Serializable
    data class Comment(
        @JvmField val listType: String? = null,
        @JvmField val updateTs: Long = 0,
        @JvmField val latestCommentText: String? = null,
        @JvmField val opToken: String? = null,
        @JvmField val canAnonymous: Boolean = false
    ) {
        companion object {
            const val TYPE_COMMENT = "comment"
            const val TYPE_BOARD = "board"
        }
    }
}