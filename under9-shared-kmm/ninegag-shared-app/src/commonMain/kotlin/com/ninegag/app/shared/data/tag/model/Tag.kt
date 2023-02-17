package com.ninegag.app.shared.data.tag.model

data class Tag(
    val key: String,
    val url: String,
    val isSensitive: Boolean,
    val postCount: Int = 0,  // Post count,
    val visitedCount: Int = 0,
    val tagFavStatus: TagFavStatus = TagFavStatus.UNSPECIFIED
)

enum class TagFavStatus {
    UNSPECIFIED, FAVOURITED, HIDDEN
}
