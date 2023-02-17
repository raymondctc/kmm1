package com.ninegag.app.shared.data.tag

import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.data.tag.model.TagFavStatus
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.data.tag.model.DenormalizedTagItemEntity
import com.ninegag.app.shared.db.GetTagListItemByListKey
import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.ninegag.app.shared.db.TagItemEntity
import com.ninegag.app.shared.infra.remote.tag.model.ApiTag
import com.under9.shared.core.result.Result
import com.under9.shared.core.util.PlatformUtils
import com.under9.shared.infra.db.SQLOrder
import io.github.aakira.napier.Napier


interface LocalTagDataSource {
    fun refreshTagListByListKey(listKey: String, tags: List<ApiTag>)
    fun getNavTagList(maxRecentShownItems: Int): Result<Map<String, List<DenormalizedTagItemEntity>>>
    fun getCustomizeHomePageTagList(): Result<Map<String, List<DenormalizedTagItemEntity>>>
    fun getTagListByListKey(
        types: List<String>,
        limit: Long,
        sqlOrder: SQLOrder = SQLOrder.ASC
    ): Result<Map<String, List<DenormalizedTagItemEntity>>>
    fun refreshAndStoreHistoryTag(tags: List<Tag>, keepAmount: Int)
    fun updateTagFavOrHiddenStatusByUrl(url: String, tagFavStatus: TagFavStatus)

    /**
     * @param url tagUrl
     * @param maxRecentItems Number of recent items to be saved
     */
    fun updateTagRecentStatusByUrl(url: String, maxRecentItems: Int)
    fun clearRecentItems()
}

class LocalTagDataSourceImpl(
    private val database: SharedNineGagDatabase
) : LocalTagDataSource {

    override fun refreshTagListByListKey(listKey: String, tags: List<ApiTag>) {

        database.tagListQueries.transaction {
            // Clear list first
            database.tagListQueries.clearTagListItemByListKey(listKey)
            Napier.d("database.tagListQueries, clearTagListItemByListKey=$listKey, thread=${PlatformUtils.currentThreadInfo}")

            tags.forEach {
                Napier.d("database.tagListQueries, replaceItem, tags.size=${tags.size}")
                val tagItem = database.tagListQueries.getTagByUrl(it.url).executeAsOneOrNull()
                if (tagItem == null) {
                    database.tagListQueries.addTagItem(it.url, it.key, if (it.isSensitive == 1) 1L else 0L, it.description)
                } else {
                    database.tagListQueries.replaceTagItemByUrl(it.url, it.key, it.description, it.url)
                }

                val lastItem = database.tagListQueries.getTagByUrl(it.url).executeAsOne()

                Napier.d("database.tagListQueries, replaceItem, listKey=$listKey, it.key=${it.key}, it.url=${it.url} lastId=${lastItem.id}")
                val tagList = database.tagListQueries.getTagListByKey(listKey).executeAsOneOrNull()
                if (tagList == null) {
                    Napier.d("database.addToTagList")
                    database.tagListQueries.addToTagList(listKey)
                }

                Napier.d("database.addTagToListItem, null item, lastItem=${lastItem.tag_key}")
                database.tagListQueries.addTagToListItem(
                    lastItem.url,
                    listKey,
                    null,
                    null,
                    null,
                    null
                )
            }
        }

    }

    override fun getNavTagList(
        maxRecentShownItems: Int
    ): Result<Map<String, List<DenormalizedTagItemEntity>>> {
        val types = listOf(
            TagListModel.LIST_FAVOURITE,
            TagListModel.LIST_RECENT,
            TagListModel.LIST_POPULAR,
            TagListModel.LIST_OTHER,
            TagListModel.LIST_HIDDEN,
        )

        val map = getTagListWithTypes(types, Long.MAX_VALUE, SQLOrder.ASC).toMutableMap()

        map[TagListModel.LIST_FAVOURITE]?.let {
            map[TagListModel.LIST_FAVOURITE] = it.reversed()
        }
        map[TagListModel.LIST_RECENT]?.let { tagItems ->
            val newList = tagItems.reversed()

            map[TagListModel.LIST_RECENT] = if (newList.size > maxRecentShownItems) {
                newList.subList(0, maxRecentShownItems)
            } else {
                newList
            }
        }

        Napier.d("getNavTagList, thread=${PlatformUtils.currentThreadInfo}")

        return Result.Success(map)
    }

    override fun getCustomizeHomePageTagList(): Result<Map<String, List<DenormalizedTagItemEntity>>> {
        val types = listOf(
            TagListModel.LIST_FAVOURITE,
            TagListModel.LIST_POPULAR,
            TagListModel.LIST_OTHER,
            TagListModel.LIST_HIDDEN,
        )

        val map = getTagListWithTypes(types, Long.MAX_VALUE, SQLOrder.ASC).toMutableMap()

        map[TagListModel.LIST_FAVOURITE]?.let {
            map[TagListModel.LIST_FAVOURITE] = it.reversed()
        }
        map[TagListModel.LIST_RECENT]?.let {
            map[TagListModel.LIST_RECENT] = it.reversed()
        }

        Napier.d("getNavTagList, thread=${PlatformUtils.currentThreadInfo}")

        return Result.Success(map)
    }

    override fun getTagListByListKey(
        types: List<String>,
        limit: Long,
        sqlOrder: SQLOrder
    ): Result<Map<String, List<DenormalizedTagItemEntity>>> {
        return Result.Success(getTagListWithTypes(types, limit, sqlOrder))
    }

    private fun getTagListWithTypes(
        types: List<String>,
        limit: Long,
        sqlOrder: SQLOrder
    ): Map<String, List<DenormalizedTagItemEntity>> {
        val list = if (sqlOrder == SQLOrder.ASC) {
            database.tagListQueries.getTagListItemByListKey(types, limit).executeAsList()
        } else {
            database.tagListQueries.getTagListItemByListKeyDesc(types, limit).executeAsList().map {
                GetTagListItemByListKey(
                    it.id, it.url, it.tag_key,
                    it.is_sensitive, it.description, it.list_key,
                    it.fav_ts_order, it.hidden_ts_order, it.recent_ts_order,
                    it.visited_count
                )
            }
        }
        val map = LinkedHashMap<String, MutableList<DenormalizedTagItemEntity>>()
        list.forEach { item ->
            val listKey = item.list_key
            val tagItemEntity = mapToEntityModel(item)
            if (map.containsKey(listKey)) {
                map[listKey]!!.add(tagItemEntity)
            } else {
                map[listKey] = mutableListOf(tagItemEntity)
            }
        }
        Napier.i("LinkedHashMap, map=$map")
        return map
    }

    override fun refreshAndStoreHistoryTag(tags: List<Tag>, keepAmount: Int) {
        database.tagListQueries.transaction {
            val pendingAddList = ArrayList<Tag>()
            pendingAddList.addAll(tags)
            if (tags.size < keepAmount) {
                val allHistoryTags = database.tagListQueries
                    .getTagListItemByListKey(listOf(TagListModel.LIST_HISTORY), Long.MAX_VALUE)
                    .executeAsList()
                    .reversed()

                allHistoryTags.forEach {
                    pendingAddList.add(Tag(
                        it.tag_key,
                        it.url,
                        false
                    ))
                }
            }

            database.tagListQueries.clearTagListItemByListKey(TagListModel.LIST_HISTORY)

            val tagList = database.tagListQueries.getTagListByKey(TagListModel.LIST_HISTORY).executeAsOneOrNull()

            if (tagList == null) {
                database.tagListQueries.addToTagList(TagListModel.LIST_HISTORY)
            }

            var addedAmount = 0
            if (pendingAddList.size > 0) {
                do {
                    val currentPendingItem = pendingAddList[addedAmount]
                    var item = database.tagListQueries.getTagByUrl(currentPendingItem.url).executeAsOneOrNull()
                    if (item == null) {
                        database.tagListQueries.addTagItem(currentPendingItem.url, currentPendingItem.key, 0, null)
                        // refresh to get latest item
                        item = database.tagListQueries.getTagByUrl(currentPendingItem.url).executeAsOneOrNull()
                    }

                    database.tagListQueries.addTagToListItem(
                        item!!.url,
                        TagListModel.LIST_HISTORY,
                        null,
                        null,
                        null,
                        null
                    )
                    ++addedAmount
                } while (addedAmount != keepAmount && pendingAddList.size > addedAmount)
            }
        }
    }

    override fun updateTagFavOrHiddenStatusByUrl(url: String, tagFavStatus: TagFavStatus) {
        val tagItem = database.tagListQueries.getTagListItemByUrl(url).executeAsList()
        val acceptableListKeysForUpdating = setOf(
            TagListModel.LIST_POPULAR,
            TagListModel.LIST_FAVOURITE,
            TagListModel.LIST_HIDDEN,
            TagListModel.LIST_OTHER,
            TagListModel.LIST_RECENT
        )
        tagItem.forEach {
            if (acceptableListKeysForUpdating.contains(it.list_key)) {
                Napier.d("updateTagFavOrHiddenStatusByUrl, item=${it.url}")
                when (tagFavStatus) {
                    // if tag fav status is unspecified, check for the list if it contains fav/hidden items
                    // if yes, update ts to null, remove tag list item
                    TagFavStatus.UNSPECIFIED -> {
                        val result = database.tagListQueries.getTagListItemByUrl(it.url)
                            .executeAsList()
                        for (item in result) {
                            if (item.list_key == TagListModel.LIST_HIDDEN ||
                                item.list_key == TagListModel.LIST_FAVOURITE) {
                                database.tagListQueries.removeTagFromList(item.list_key, item.url)
                            }
                        }
                    }

                    TagFavStatus.FAVOURITED -> {
                        database.tagListQueries.transaction {
                            database.tagListQueries.removeTagFromList(
                                TagListModel.LIST_HIDDEN,
                                it.url
                            )
                            database.tagListQueries.addTagToListItem(
                                it.url,
                                TagListModel.LIST_FAVOURITE,
                                PlatformUtils.currentTimeMillis,
                                null,
                                null,
                                null
                            )
                        }
                    }

                    TagFavStatus.HIDDEN -> {
                        database.tagListQueries.transaction {
                            database.tagListQueries.removeTagFromList(
                                TagListModel.LIST_FAVOURITE,
                                it.url
                            )
                            database.tagListQueries.addTagToListItem(
                                it.url,
                                TagListModel.LIST_HIDDEN,
                                null,
                                null,
                                PlatformUtils.currentTimeMillis,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    override fun updateTagRecentStatusByUrl(url: String, maxRecentItems: Int) {
        val tagItem = database.tagListQueries.getTagListItemByUrl(url).executeAsList()
        val acceptableListKeysForUpdatingRecents = setOf(
            TagListModel.LIST_POPULAR,
            TagListModel.LIST_FAVOURITE,
            TagListModel.LIST_HIDDEN,
            TagListModel.LIST_OTHER,
            TagListModel.LIST_RECENT
        )
        run breaking@ {
            tagItem.forEach {
                if (acceptableListKeysForUpdatingRecents.contains(it.list_key)) {
                    val item = database.tagListQueries.getTagListItemByTagUrlAndListKey(it.url, TagListModel.LIST_RECENT)
                        .executeAsOneOrNull()
                    val visitedCount = item?.visited_count ?: 0

                    Napier.d("updateTagRecentStatusByUrl, item=${it.url}, itemKey=${it.tag_key}, itemId=${it.id}, itemListKey=${it.list_key}, visitedCount=$visitedCount")

                    database.tagListQueries.addTagToListItem(
                        it.url, TagListModel.LIST_RECENT,
                        null,
                        PlatformUtils.currentTimeMillis,
                        null,
                        visitedCount + 1
                    )

                    val recentList = database.tagListQueries.getTagListItemByListKey(listOf(TagListModel.LIST_RECENT), Long.MAX_VALUE)
                        .executeAsList()

                    if (recentList.size > maxRecentItems) {
                        database.tagListQueries.removeTagFromList(TagListModel.LIST_RECENT, recentList.first().url)
                    }
                    return@breaking
                }
            }
        }

    }

    override fun clearRecentItems() {
        database.tagListQueries.clearTagListItemByListKey(TagListModel.LIST_RECENT)
    }

    private fun mapToEntityModel(item: GetTagListItemByListKey): DenormalizedTagItemEntity {
        return DenormalizedTagItemEntity(
            id = item.id,
            url = item.url,
            tag_key = item.tag_key,
            is_sensitive = item.is_sensitive,
            description = item.description,
            visitedCount = item.visited_count?.toInt() ?: 0
        )
    }
}
