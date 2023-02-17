package com.ninegag.app.shared.ui.tag.model

import com.ninegag.app.shared.data.tag.model.TagListModel
import dev.icerock.moko.resources.desc.StringDesc

interface NavItemUiModel {
}

data class TagSectionedUiModel(
    val title: StringDesc,
    val sectionType: SectionType,
    val tags: List<TagUiModel>
) : NavItemUiModel

data class TagUiModel(
    val title: String,
    val url: String,
    val status: TagFavStatus = TagFavStatus.UNSPECIFIED,
    val isSensitive: Boolean
)

enum class TagFavStatus {
    UNSPECIFIED, FAVOURITED, HIDDEN
}

enum class SectionType(val type: String) {
    POPULAR(TagListModel.LIST_POPULAR),
    OTHER(TagListModel.LIST_OTHER),
    RECENT(TagListModel.LIST_RECENT),
    HIDDEN(TagListModel.LIST_HIDDEN),
    FAVOURITED(TagListModel.LIST_FAVOURITE)
}