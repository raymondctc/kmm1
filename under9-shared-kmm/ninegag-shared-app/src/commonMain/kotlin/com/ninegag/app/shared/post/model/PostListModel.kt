package com.ninegag.app.shared.post.model

data class PostListModel(
    val didEndOfList: Int = 0,
//    val tag: ApiTag? = null,
    val posts: List<PostModel> = mutableListOf(),
//    val targetedAdTags: JsonElement? = null,
//    val featuredAds: List<ApiFeaturedAds>? = null,
//    val relatedTags: List<ApiTag>? = null,
    val after: String? = null,
//    val feedId: String? = null
)