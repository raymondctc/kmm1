package com.ninegag.app.shared.ui.tag

import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.domain.tag.FetchNavTagListUseCase
import com.ninegag.app.shared.domain.tag.UpdateFavHiddenRecentStatusUseCase
import com.ninegag.app.shared.ui.tag.model.SectionType
import com.ninegag.app.shared.ui.tag.model.TagFavStatus
import com.ninegag.app.shared.ui.tag.model.TagSectionedUiModel
import com.ninegag.app.shared.ui.tag.model.TagUiModel
import com.ninegag.app.shared.util.NinegagString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NavTagListUiHandler(
    private val coroutineScope: CoroutineScope,
    private val fetchNavTagListUseCase: FetchNavTagListUseCase,
    private val updateFavHiddenRecentStatusUseCase: UpdateFavHiddenRecentStatusUseCase
) {
    companion object {
        internal const val MAX_RECENT_ITEMS = 3
    }

    fun favItem(
        tagSectionedUiModelIndex: Int,
        tagItemIndex: Int,
        uiModels: List<TagSectionedUiModel>,
        sparedItems: List<TagUiModel>
    ): Pair<List<TagSectionedUiModel>, List<TagUiModel>> {
        val newList = uiModels.toMutableList()
        val sectionUiModel = newList[tagSectionedUiModelIndex]

        val favSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.FAVOURITED }
        val recentSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.RECENT }
        val popularListIndex = newList.indexOfFirst { it.sectionType == SectionType.POPULAR }
        val newTagList = sectionUiModel.tags.toMutableList()
        val newTag = sectionUiModel.tags[tagItemIndex].copy(
            status = TagFavStatus.FAVOURITED
        )

        // Check if we should update tag status in recent list
        if (recentSectionIndex != -1) {
            val recentListSection = newList[recentSectionIndex]
            val tagIndexInRecentList = recentListSection.tags.indexOfFirst { it.url == newTag.url }
            if (tagIndexInRecentList != -1) {
                val recentList = recentListSection.tags.toMutableList().apply {
                    set(tagIndexInRecentList, newTag)
                }
                newList[recentSectionIndex] = newList[recentSectionIndex].copy(
                    tags = recentList
                )
            }
        }

        // If not clicking on recent list, remove item
        if (sectionUiModel.sectionType != SectionType.RECENT) {
            newTagList.removeAt(tagItemIndex)
            newList[tagSectionedUiModelIndex] = newList[tagSectionedUiModelIndex].copy(
                tags = newTagList
            )
        } else {
            // Scan for other lists
            // remove items from other list
            for (i in 0 until newList.size) {
                if (i == recentSectionIndex) continue

                val index = newList[i].tags.indexOfFirst { it.url == newTag.url }
                if (index != -1) {
                    val newOtherList = newList[i].tags.toMutableList().also {
                        it.removeAt(index)
                    }
                    newList[i] = newList[i].copy(
                        tags = newOtherList
                    )
                    break
                }
            }
        }

        val newSparedItemList = sparedItems.toMutableList()
        if (popularListIndex != -1 && sparedItems.isNotEmpty()) {
            val newPopularList = newList[popularListIndex].tags.toMutableList()
            newPopularList.add(newSparedItemList.removeFirst())
            newList[popularListIndex] = newList[popularListIndex].copy(
                tags = newPopularList
            )
        }

        // Create a new favourited section for new item
        if (favSectionIndex == -1) {
            newList.add(0, TagSectionedUiModel(
                title = NinegagString.navlistHeaderFavorites(),
                sectionType = SectionType.FAVOURITED,
                tags = listOf(newTag)
            ))
        } else {
            val newFavTagList = newList[favSectionIndex].tags.toMutableList()
            newFavTagList.add(0, newTag)
            newList[favSectionIndex] = newList[favSectionIndex].copy(
                tags = newFavTagList
            )
        }

        saveFavStatusToCache(sectionUiModel.tags[tagItemIndex].url, TagFavStatus.FAVOURITED)
        return Pair(newList, newSparedItemList)
    }

    fun unFavItem(
        tagSectionedUiModelIndex: Int,
        tagItemIndex: Int,
        uiModels: List<TagSectionedUiModel>,
        originalModelList: TagListModel,
        sparedItems: List<TagUiModel>
    ): Pair<List<TagSectionedUiModel>, List<TagUiModel>> {
        val newList = uiModels.toMutableList()
        val sectionUiModel = newList[tagSectionedUiModelIndex]
        val newTag = sectionUiModel.tags[tagItemIndex].copy(
            status = TagFavStatus.UNSPECIFIED
        )

        val (originalListSectionIndex, originalSectionUiModelTagList) = assignToOriginalListIfAvailable(
            uiModels, originalModelList, newTag
        )

        // Remove from favourite list
        val favSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.FAVOURITED }
        val newFavTagList = newList[favSectionIndex].tags.toMutableList()
        val favListIndex = newFavTagList.indexOfFirst { it.url == newTag.url }
        newFavTagList.removeAt(favListIndex)

        // Update recent list
        val recentSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.RECENT }
        if (recentSectionIndex != -1) {
            val recentListSection = newList[recentSectionIndex]
            val tagIndexInRecentList = recentListSection.tags.indexOfFirst { it.url == newTag.url }
            if (tagIndexInRecentList != -1) {
                val recentList = recentListSection.tags.toMutableList().apply {
                    set(tagIndexInRecentList, newTag)
                }
                newList[recentSectionIndex] = newList[recentSectionIndex].copy(
                    tags = recentList
                )
            }
        }

        newList[favSectionIndex] = newList[favSectionIndex].copy(
            tags = newFavTagList
        )

        if (originalListSectionIndex != -1 && originalSectionUiModelTagList != null) {
            newList[originalListSectionIndex] = newList[originalListSectionIndex].copy(
                tags = originalSectionUiModelTagList
            )
        }

        val popularListIndex = newList.indexOfFirst { it.sectionType == SectionType.POPULAR }
        val newSparedItemList = sparedItems.toMutableList()

        if (originalListSectionIndex == popularListIndex) {
            if (newList[popularListIndex].tags.size >= DrawerNavUiStateMapper.MAX_POPULAR_LIST_SIZE) {
                val newPopularTags = newList[popularListIndex].tags.toMutableList()
                val removedTag = newPopularTags.removeLast()
                newSparedItemList.add(0, removedTag)
                newList[popularListIndex] = newList[popularListIndex].copy(
                    tags = newPopularTags
                )
            }
        }

        saveFavStatusToCache(sectionUiModel.tags[tagItemIndex].url, TagFavStatus.UNSPECIFIED)
        return Pair(newList, newSparedItemList)
    }

    fun hideItem(
        tagSectionedUiModelIndex: Int,
        tagItemIndex: Int,
        uiModels: List<TagSectionedUiModel>
    ): List<TagSectionedUiModel> {
        val newList = uiModels.toMutableList()
        val sectionUiModel = newList[tagSectionedUiModelIndex]
        val newTag = sectionUiModel.tags[tagItemIndex].copy(
            status = TagFavStatus.HIDDEN
        )
        val hiddenSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.HIDDEN }

        // Scan for other lists
        // remove items from other list
        for (i in 0 until newList.size) {
            val index = newList[i].tags.indexOfFirst { it.url == newTag.url }
            if (index != -1) {
                val newOtherList = newList[i].tags.toMutableList().also {
                    it.removeAt(index)
                }
                newList[i] = newList[i].copy(
                    tags = newOtherList
                )
                break
            }
        }

        if (hiddenSectionIndex == -1) {
            newList.add(
                TagSectionedUiModel(
                title = NinegagString.navlistHeaderHidden(),
                sectionType = SectionType.HIDDEN,
                tags = listOf(newTag))
            )
        } else {
            val newHiddenList = newList[hiddenSectionIndex].tags.toMutableList()
            newHiddenList.add(newTag)
            newList[hiddenSectionIndex] = newList[hiddenSectionIndex].copy(
                tags = newHiddenList
            )
        }

        saveFavStatusToCache(newTag.url, TagFavStatus.HIDDEN)
        return newList
    }

    fun unHideItem(
        tagSectionedUiModelIndex: Int,
        tagItemIndex: Int,
        uiModels: List<TagSectionedUiModel>,
        originalModelList: TagListModel
    ): List<TagSectionedUiModel> {
        val newList = uiModels.toMutableList()
        val sectionUiModel = newList[tagSectionedUiModelIndex]
        val newTag = sectionUiModel.tags[tagItemIndex].copy(
            status = TagFavStatus.UNSPECIFIED
        )

        val newSectionUiModel = sectionUiModel.tags.toMutableList().also {
            it.removeAt(tagItemIndex)
        }
        val (originalListSectionIndex, originalSectionUiModelTagList) = assignToOriginalListIfAvailable(
            uiModels, originalModelList, newTag
        )

        if (originalSectionUiModelTagList != null) {
            newList[originalListSectionIndex] = newList[originalListSectionIndex].copy(
                tags = originalSectionUiModelTagList
            )
        }

        newList[tagSectionedUiModelIndex] = newList[tagSectionedUiModelIndex].copy(
            tags = newSectionUiModel
        )

        saveFavStatusToCache(newTag.url, TagFavStatus.UNSPECIFIED)
        return newList
    }

    fun updateRecentList(
        tagSectionedUiModelIndex: Int,
        tagItemIndex: Int,
        uiModels: List<TagSectionedUiModel>
    ): List<TagSectionedUiModel> {
        val newList = uiModels.toMutableList()
        val sectionUiModel = newList[tagSectionedUiModelIndex]
        val tag = sectionUiModel.tags[tagItemIndex]

        val favSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.FAVOURITED }
        val recentSectionIndex = newList.indexOfFirst { it.sectionType == SectionType.RECENT }
        if (recentSectionIndex == -1) {
            newList.add(favSectionIndex + 1, TagSectionedUiModel(
                title = NinegagString.navlistHeaderRecents(),
                sectionType = SectionType.RECENT,
                tags = listOf(tag.copy())
            ))
        } else {
            var newTagList = newList[recentSectionIndex].tags.toMutableList()
            val existingTag = newTagList.find { it.url == tag.url }

            // Exist, put to top
            if (existingTag != null) {
                newTagList.remove(existingTag)
                newTagList.add(0, tag.copy())
            } else {
                newTagList.add(0, tag.copy())
                if (newTagList.size > MAX_RECENT_ITEMS) {
                    newTagList = newTagList.subList(0, MAX_RECENT_ITEMS)
                }
            }

            newList[recentSectionIndex] = newList[recentSectionIndex].copy(
                tags = newTagList
            )
        }

        coroutineScope.launch {
            updateFavHiddenRecentStatusUseCase(
                UpdateFavHiddenRecentStatusUseCase.UpdateRecentSectionParam(tag.url)
            )
        }

        return newList
    }

    // Lookup original location (from API)
    /**
     * Inserting an item back to the original list position if available
     * @param uiModels
     * @param originalModelList
     * @param newTag
     * @return A pair. The original index of the removed item and also the updated list. Returning -1 and null if item is not found in [originalModelList]
     */
    private fun assignToOriginalListIfAvailable(
        uiModels: List<TagSectionedUiModel>,
        originalModelList: TagListModel,
        newTag: TagUiModel
    ): Pair<Int, List<TagUiModel>?> {
        var originalIndex = 0
        var originalSectionType: SectionType? = null
        var originalSectionKey: String? = null
        for (entry in originalModelList.tagList) {
            val tagList = originalModelList.tagList[entry.key]
            originalIndex = tagList?.indexOfFirst { newTag.url == it.url } ?: -1
            if (originalIndex != -1) {
                originalSectionType = SectionType.values().find { it.type == entry.key }
                originalSectionKey = entry.key
                break
            }
        }

        // To sort according to the original API order
        val originalListSectionIndex = uiModels.indexOfFirst { originalSectionType == it.sectionType }
        if (originalListSectionIndex != -1) {
            val originalSectionUiModelTagList = uiModels[originalListSectionIndex].tags.toMutableList()
            val sortingReference = originalModelList.tagList[originalSectionKey]!!.withIndex().associate { it.value.url to it.index }
            originalSectionUiModelTagList.add(newTag)
            originalSectionUiModelTagList.sortBy { sortingReference[it.url] }
            return Pair(originalListSectionIndex, originalSectionUiModelTagList)
        } else {
            return Pair(-1, null)
        }
    }

    /**
     * @param url The url of the tag
     * @param favStatus
     */
    private fun saveFavStatusToCache(
        url: String,
        favStatus: TagFavStatus
    ) {
        coroutineScope.launch {
            val mappedTagFavStatus = when (favStatus) {
                TagFavStatus.UNSPECIFIED -> com.ninegag.app.shared.data.tag.model.TagFavStatus.UNSPECIFIED
                TagFavStatus.FAVOURITED -> com.ninegag.app.shared.data.tag.model.TagFavStatus.FAVOURITED
                TagFavStatus.HIDDEN -> com.ninegag.app.shared.data.tag.model.TagFavStatus.HIDDEN
            }
            updateFavHiddenRecentStatusUseCase(
                UpdateFavHiddenRecentStatusUseCase.UpdateFavHiddenStatusParam(
                    url, mappedTagFavStatus
                )
            )
        }
    }

    suspend fun migrateLegacyFavStatusToCache(
        url: String,
        favStatus: TagFavStatus
    ) {
        val mappedTagFavStatus = when (favStatus) {
            TagFavStatus.UNSPECIFIED -> com.ninegag.app.shared.data.tag.model.TagFavStatus.UNSPECIFIED
            TagFavStatus.FAVOURITED -> com.ninegag.app.shared.data.tag.model.TagFavStatus.FAVOURITED
            TagFavStatus.HIDDEN -> com.ninegag.app.shared.data.tag.model.TagFavStatus.HIDDEN
        }
        updateFavHiddenRecentStatusUseCase(
            UpdateFavHiddenRecentStatusUseCase.MigrateLegacyGroupFavHiddenStatusParam(
                url, mappedTagFavStatus
            )
        )
    }

    suspend fun migrateRecentLegacyRecent(url: String) {
        updateFavHiddenRecentStatusUseCase(
            UpdateFavHiddenRecentStatusUseCase.MigrateLegacyGroupRecentStatusParam(url)
        )
    }
}