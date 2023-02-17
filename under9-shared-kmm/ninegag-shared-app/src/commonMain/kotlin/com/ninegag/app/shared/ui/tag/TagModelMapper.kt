package com.ninegag.app.shared.ui.tag

import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.ui.tag.model.TagFavStatus
import com.ninegag.app.shared.ui.tag.model.TagUiModel
import com.ninegag.app.shared.util.Mapper

object TagModelMapper: Mapper<TagUiModel, Tag> {
    override fun mapTo(from: TagUiModel): Tag {
        val mappedTagFavStatus = when (from.status) {
            TagFavStatus.UNSPECIFIED -> com.ninegag.app.shared.data.tag.model.TagFavStatus.UNSPECIFIED
            TagFavStatus.FAVOURITED -> com.ninegag.app.shared.data.tag.model.TagFavStatus.FAVOURITED
            TagFavStatus.HIDDEN -> com.ninegag.app.shared.data.tag.model.TagFavStatus.HIDDEN
        }

        return Tag(
            key = from.title,
            url = from.url,
            isSensitive = from.isSensitive,
            tagFavStatus = mappedTagFavStatus
        )
    }
}

fun TagUiModel.toDataModel(): Tag {
    return TagModelMapper.mapTo(this)
}