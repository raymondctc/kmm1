package com.ninegag.app.shared.data.tag.model

data class TagListModel(
    val tagList: Map<String, List<Tag>>
) {
    companion object {
        const val LIST_HOME = "home"
        const val LIST_OTHER = "other"
        const val LIST_POPULAR = "popular"
        const val LIST_POPULAR_SPARED = "_popular_spared"
        const val LIST_TRENDING = "trending"

        // Local list
        const val LIST_FAVOURITE = "_favourite"
        const val LIST_HIDDEN = "_hidden"
        const val LIST_RECENT = "_recent"
        const val LIST_HISTORY = "_history"
    }
}