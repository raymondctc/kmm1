package com.ninegag.app.shared.ui.tag

import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.data.tag.model.TagFavStatus
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.util.Mapper
import com.ninegag.app.shared.ui.tag.model.SectionType
import com.ninegag.app.shared.ui.tag.model.TagSectionedUiModel
import com.ninegag.app.shared.ui.tag.model.TagUiModel
import com.ninegag.app.shared.util.NinegagString
import dev.icerock.moko.resources.desc.StringDesc

data class UiModelsAndOriginalList(
    val uiModels: List<TagSectionedUiModel>,
    val sparedPopularItems: List<TagUiModel>,
    val originalModelList: TagListModel,
)

object DrawerNavUiStateMapper: Mapper<TagListModel, UiModelsAndOriginalList> {
    internal const val MAX_POPULAR_LIST_SIZE = 10

    private fun filterList(
        lookupList: List<Tag>,
        filterList: List<Tag>
    ): List<Tag> {
        val lookupKeySet = lookupList.map { it.url }.toSet()
        return filterList.filter { !lookupKeySet.contains(it.url) }
    }

    private fun mapTagListToUiList(
        list: List<Tag>,
        limit: Int = Int.MAX_VALUE
    ): List<TagUiModel> {
        val tagUiModels = mutableListOf<TagUiModel>()
        for (i in list.indices) {
            if (i >= limit) break

            val tag = list[i]
            val tagUiModel = TagUiModel(
                title = tag.key,
                url = tag.url,
                status = when (tag.tagFavStatus) {
                    TagFavStatus.FAVOURITED -> com.ninegag.app.shared.ui.tag.model.TagFavStatus.FAVOURITED
                    TagFavStatus.HIDDEN -> com.ninegag.app.shared.ui.tag.model.TagFavStatus.HIDDEN
                    else -> com.ninegag.app.shared.ui.tag.model.TagFavStatus.UNSPECIFIED
                },
                isSensitive = tag.isSensitive
            )
            tagUiModels.add(tagUiModel)
        }
        return tagUiModels
    }

    private fun mapToTagSectionUiModel(
        title: StringDesc,
        sectionType: SectionType,
        list: List<Tag>,
        limit: Int = Int.MAX_VALUE
    ): TagSectionedUiModel {
        return TagSectionedUiModel(
            title = title,
            sectionType = sectionType,
            tags = mapTagListToUiList(list, limit)
        )
    }

    override fun mapTo(from: TagListModel): UiModelsAndOriginalList {
        val favList = from.tagList[TagListModel.LIST_FAVOURITE]
        val recentList = from.tagList[TagListModel.LIST_RECENT]
        val popularList = from.tagList[TagListModel.LIST_POPULAR]
        val otherList = from.tagList[TagListModel.LIST_OTHER]
        val hiddenList = from.tagList[TagListModel.LIST_HIDDEN]

        // Filter out duplicated tags by URL
        // https://app.clickup.com/t/2muh3w6?comment=962271368
        val lookupList = mutableListOf<Tag>()
        val sparedPopularList = mutableListOf<TagUiModel>()

        favList?.let {
            lookupList.addAll(it)
        }
        hiddenList?.let {
            lookupList.addAll(it)
        }

        val filteredPopularList = popularList?.let {
            filterList(lookupList, it)
        }

        popularList?.let {
            lookupList.addAll(it)
        }
        val filteredOtherList = otherList?.let {
            filterList(lookupList, it)
        }

        val filteredUiList = mutableListOf<TagSectionedUiModel>()

        favList?.let {
            filteredUiList.add(mapToTagSectionUiModel(
                title = NinegagString.navlistHeaderFavorites(),
                sectionType = SectionType.FAVOURITED,
                it
            ))
        }
        recentList?.let {
            filteredUiList.add(mapToTagSectionUiModel(
                title = NinegagString.navlistHeaderRecents(),
                sectionType = SectionType.RECENT,
                it
            ))
        }
        filteredPopularList?.let {
            filteredUiList.add(mapToTagSectionUiModel(
                title = NinegagString.navlistHeaderPopular(),
                sectionType = SectionType.POPULAR,
                it,
                limit = MAX_POPULAR_LIST_SIZE
            ))

            if (filteredPopularList.size > MAX_POPULAR_LIST_SIZE) {
                sparedPopularList.addAll(
                    it.subList(MAX_POPULAR_LIST_SIZE, filteredPopularList.size).map { tag ->
                        val mappedFavStatus = when (tag.tagFavStatus) {
                            TagFavStatus.FAVOURITED -> com.ninegag.app.shared.ui.tag.model.TagFavStatus.FAVOURITED
                            TagFavStatus.UNSPECIFIED -> com.ninegag.app.shared.ui.tag.model.TagFavStatus.UNSPECIFIED
                            TagFavStatus.HIDDEN -> com.ninegag.app.shared.ui.tag.model.TagFavStatus.HIDDEN
                        }
                        TagUiModel(tag.key, tag.url, mappedFavStatus, tag.isSensitive)
                    }
                )
            }
        }
        filteredOtherList?.let {
            filteredUiList.add(mapToTagSectionUiModel(
                title = NinegagString.navlistHeaderOtherTags(),
                sectionType = SectionType.OTHER,
                it
            ))
        }
        hiddenList?.let {
            filteredUiList.add(mapToTagSectionUiModel(
                title = NinegagString.navlistHeaderHidden(),
                sectionType = SectionType.HIDDEN,
                it
            ))
        }

        return UiModelsAndOriginalList(
            filteredUiList,
            sparedPopularList,
            TagListModel(
                mapOf(
                    TagListModel.LIST_POPULAR to (from.tagList[TagListModel.LIST_POPULAR] ?: emptyList()),
                    TagListModel.LIST_OTHER to (from.tagList[TagListModel.LIST_OTHER] ?: emptyList()),
                )
            )
        )
    }
}