package com.ninegag.app.shared.data.tag.model

/**
 * A wrapped class for binding denormalized data
 * Fields are from [com.ninegag.app.shared.db.TagItemEntiy]
 */
data class DenormalizedTagItemEntity(
    val id: Long,
    val url: String,
    val tag_key: String,
    val is_sensitive: Long,
    val description: String?,
    val visitedCount: Int
)