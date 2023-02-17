package com.ninegag.app.shared.data.tag

import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.data.tag.model.TagFavStatus
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.data.tag.model.DenormalizedTagItemEntity
import com.under9.shared.core.result.Result
import com.under9.shared.core.result.data
import com.under9.shared.infra.db.SQLOrder
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TagListRepository {
    /**
     * @param maxShownRecentItems The maximum number of recent items to be shown
     * Get list of navigation tags
     */
    fun getNavTagList(maxShownRecentItems: Int): Flow<Result<TagListModel>>
    fun getCustomizeHomePageTagList(): Flow<Result<TagListModel>>
    fun getHomeTagList(): Flow<Result<TagListModel>>
    fun getTrendTagList(): Flow<Result<TagListModel>>
    fun getHistoryTagList(): Flow<Result<TagListModel>>
    fun getHiddenTagList(limit: Long): Flow<Result<TagListModel>>
    fun getFavTagList(limit: Long): Flow<Result<TagListModel>>
    fun getRecentTagList(limit: Long): Flow<Result<TagListModel>>
    fun refreshAndStoreHistoryTag(tags: List<Tag>, keepAmount: Int)
    fun getRelatedTags(tag: String): Flow<Result<List<Tag>>>
    suspend fun updateTagFavOrHiddenStatusByUrl(url: String, tagFavStatus: TagFavStatus)
    suspend fun updateTagRecentStatusByUrl(url: String, maxRecentItems: Int)
    suspend fun clearRecentItems()
}

class TagListRepositoryImpl(
    private val remoteTagDataSource: RemoteTagDataSource,
    private val localTagDataSource: LocalTagDataSource
) : TagListRepository {

    override fun getHomeTagList(): Flow<Result<TagListModel>> {
        return flow {
            val localResults = localTagDataSource.getTagListByListKey(
                listOf(TagListModel.LIST_HOME),
                Long.MAX_VALUE
            )
            val localGetResult = localResults.getOrNull()
            localGetResult?.let { item ->
                val list = item[TagListModel.LIST_HOME]
                list?.let {
                    emit(Result.Success(
                        TagListModel(
                            mapOf(
                                TagListModel.LIST_HOME to it.map { tagItemEntity ->
                                    Tag(tagItemEntity.tag_key, tagItemEntity.url, false)
                                }
                            )
                        )
                    ))
                }
            }

            val remoteResult = remoteTagDataSource.getNavTagList()
            if (remoteResult.isSuccess()) {
                remoteResult.getOrNull()?.let {
                    localTagDataSource.refreshTagListByListKey(TagListModel.LIST_HOME, it.homeTags)
                    emit(Result.Success(
                        TagListModel(mapOf(
                            TagListModel.LIST_HOME to (it.homeTags).map { tag ->
                                Tag(tag.key, tag.url, false)
                            }
                        ))
                    ))
                }
            } else {
                localGetResult?.let {
                    if (it.isEmpty()) {
                        emit(Result.Error(remoteResult.exceptionOrNull() ?: RuntimeException("getHomeTagList unknown error")))
                    } else {
                        val r = Result.Success(mapToModel(it))
                        Napier.d("local result=" + r.data.tagList)
                        emit(r)
                    }
                }
            }
        }
    }

    override fun getNavTagList(maxShownRecentItems: Int): Flow<Result<TagListModel>> {
        return flow {
            val localResults = localTagDataSource.getNavTagList(maxShownRecentItems)
            val localGetResult = localResults.getOrNull()
            localGetResult?.let {
                val mappedModels = mapToModel(it)
                val r = Result.Success(mappedModels)
                Napier.d("local result=" + r.data.tagList)
                emit(r)
            }

            val remoteResult = fetchRemoteNavList(maxShownRecentItems)
            if (remoteResult.isSuccess()) {
                val mappedRemoteResult = remoteResult
                emit(mappedRemoteResult)
            } else {
                localGetResult?.let {
                    val mappedModels = mapToModel(it)
                    val r = Result.Success(mappedModels)
                    Napier.d("local result=" + r.data.tagList)
                    emit(r)
                }
            }
        }
    }

    override fun getCustomizeHomePageTagList(): Flow<Result<TagListModel>> {
        return flow {
            val localResults = localTagDataSource.getCustomizeHomePageTagList()
            val localGetResult = localResults.getOrNull()
            localGetResult?.let {
                val r = Result.Success(mapToModel(it))
                Napier.d("local result=" + r.data.tagList)
                emit(r)
            }
        }
    }

    override fun getTrendTagList(): Flow<Result<TagListModel>> {
        return flow {
            val localResults = localTagDataSource.getTagListByListKey(
                listOf(TagListModel.LIST_TRENDING),
                Long.MAX_VALUE
            )

            if (localResults.data != null && localResults.data!!.isNotEmpty()) {
                val r = Result.Success(mapToModel(localResults.data!!))
                emit(r)
            } else {
                val remoteResult = fetchRemoteNavList(0)
                if (remoteResult.isSuccess()) {
                    localTagDataSource.getTagListByListKey(
                        listOf(TagListModel.LIST_TRENDING),
                        Long.MAX_VALUE
                    ).getOrNull()?.let {
                        emit(Result.Success(mapToModel(it)))
                    } ?: kotlin.run {
                        emit(Result.Success(mapToModel(emptyMap())))
                    }
                } else {
                    emit(remoteResult)
                }
            }
        }
    }


    override fun getHistoryTagList(): Flow<Result<TagListModel>> {
        return flow {
            val result = localTagDataSource.getTagListByListKey(
                listOf(TagListModel.LIST_HISTORY),
                Long.MAX_VALUE
            )
            result.getOrNull()?.let {
                emit(Result.Success(mapToModel(it)))
            }?: kotlin.run {
                emit(Result.Success(mapToModel(emptyMap())))
            }
        }
    }

    override fun getHiddenTagList(limit: Long): Flow<Result<TagListModel>> {
        return flow {
            val result = localTagDataSource.getTagListByListKey(
                listOf(TagListModel.LIST_HIDDEN),
                limit,
                SQLOrder.DESC
            )
            result.getOrNull()?.let {
                emit(Result.Success(mapToModel(it)))
            } ?: run {
                emit(Result.Success(mapToModel(emptyMap())))
            }
        }
    }

    override fun getFavTagList(limit: Long): Flow<Result<TagListModel>> {
        return flow {
            val result = localTagDataSource.getTagListByListKey(
                listOf(TagListModel.LIST_FAVOURITE),
                limit,
                SQLOrder.DESC
            )
            result.getOrNull()?.let {
                emit(Result.Success(mapToModel(it)))
            } ?: run {
                emit(Result.Success(mapToModel(emptyMap())))
            }
        }
    }

    override fun getRecentTagList(limit: Long): Flow<Result<TagListModel>> {
        return flow {
            val result = localTagDataSource.getTagListByListKey(
                listOf(TagListModel.LIST_RECENT),
                limit,
                SQLOrder.DESC
            )
            result.getOrNull()?.let {
                emit(Result.Success(mapToModel(it)))
            } ?: run {
                emit(Result.Success(mapToModel(emptyMap())))
            }
        }
    }

    private suspend fun fetchRemoteNavList(
        maxRecentItems: Int
    ): Result<TagListModel> {
        val result = remoteTagDataSource.getNavTagList()
        return if (result.isSuccess()) {
            val remoteGetResult = result.getOrNull()!!
            Napier.d("remote result=" + remoteGetResult.tagLists)

            remoteGetResult.tagLists.forEach {
                localTagDataSource.refreshTagListByListKey(it.key, it.value)
            }

            val refreshedList = localTagDataSource.getNavTagList(maxRecentItems)
            val refreshedLocalGetResult = refreshedList.getOrNull()
            Napier.d("refreshed result=${refreshedLocalGetResult}")
            refreshedLocalGetResult?.let {
                Result.Success(mapToModel(it))
            } ?: Result.Error(RuntimeException("Refresh local error, result=$result"))
        } else {
            Result.Error(result.exceptionOrNull() ?: RuntimeException("Fetch remote Unknown error, result=$result"))
        }
    }

    private fun mapToModel(
        localGetResult: Map<String, List<DenormalizedTagItemEntity>>
    ): TagListModel {
        val tagListModelMap = mutableMapOf<String, List<Tag>>()

        val favSets = localGetResult[TagListModel.LIST_FAVOURITE]?.map { it.url }?.toSet()
        val hiddenSets = localGetResult[TagListModel.LIST_HIDDEN]?.map { it.url }?.toSet()

        localGetResult.forEach { entry ->
            val tagList = entry.value.map {
                var tagFavStatus: TagFavStatus = TagFavStatus.UNSPECIFIED
                if (favSets != null && favSets.contains(it.url)) {
                    tagFavStatus = TagFavStatus.FAVOURITED
                }
                if (hiddenSets != null && hiddenSets.contains(it.url)) {
                    tagFavStatus = TagFavStatus.HIDDEN
                }
                Tag(
                    key = it.tag_key,
                    url = it.url,
                    isSensitive = it.is_sensitive == 1L,
                    postCount = 0,
                    visitedCount = it.visitedCount,
                    tagFavStatus = tagFavStatus
                )
            }
            tagListModelMap[entry.key] = tagList
        }

        return TagListModel(tagListModelMap)
    }

    override fun refreshAndStoreHistoryTag(tags: List<Tag>, keepAmount: Int){
        return localTagDataSource.refreshAndStoreHistoryTag(tags, keepAmount)
    }

    override fun getRelatedTags(tag: String): Flow<Result<List<Tag>>> {
        return flow {
            val result = remoteTagDataSource.getRelatedTagList(tag)
            if (result.isSuccess()) {
                val list = result.data!!.data.tags.map {
                    Tag(it.key, it.url, (it.isSensitive == 1), postCount = it.count)
                }
                emit(Result.Success(list))
            } else {
                emit(Result.Error(result.exceptionOrNull() ?: RuntimeException("Fetch remote Unknown error, result=$result")))
            }
        }
    }

    override suspend fun updateTagFavOrHiddenStatusByUrl(url: String, tagFavStatus: TagFavStatus) {
        localTagDataSource.updateTagFavOrHiddenStatusByUrl(url, tagFavStatus)
    }

    override suspend fun updateTagRecentStatusByUrl(url: String, maxRecentItems: Int) {
        localTagDataSource.updateTagRecentStatusByUrl(url, maxRecentItems)
    }

    override suspend fun clearRecentItems() {
        localTagDataSource.clearRecentItems()
    }
}
