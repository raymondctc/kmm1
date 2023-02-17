package com.ninegag.app.shared.data.tag

import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.data.tag.model.TagFavStatus
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.ui.tag.model.TagUiModel
import com.under9.shared.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTagListRepositoryImpl: TagListRepository {


    override fun getNavTagList(maxShownRecentItems: Int): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(emptyMap())))
        }
    }

    override fun getCustomizeHomePageTagList(): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(emptyMap())))
        }
    }

    override fun getHomeTagList(): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(emptyMap())))
        }
    }

    override fun getTrendTagList(): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(emptyMap())))
        }
    }

    override fun getHistoryTagList(): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(emptyMap())))
        }
    }

    override fun getHiddenTagList(limit: Long): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(
                mapOf(
                    TagListModel.LIST_HIDDEN to listOf(
                        Tag("hidden tag 1", "https://exmaple.com/hidden", isSensitive = false)
                    )
                )
            )))
        }
    }

    override fun getFavTagList(limit: Long): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(
                mapOf(
                    TagListModel.LIST_FAVOURITE to listOf(
                        Tag("fav tag 1", "https://exmaple.com/fav", isSensitive = false)
                    )
                )
            )))
        }
    }

    override fun getRecentTagList(limit: Long): Flow<Result<TagListModel>> {
        return flow {
            emit(Result.Success(TagListModel(emptyMap())))
        }
    }

    override fun refreshAndStoreHistoryTag(tags: List<Tag>, keepAmount: Int) {
        // Do nothing
    }

    override fun getRelatedTags(tag: String): Flow<Result<List<Tag>>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTagFavOrHiddenStatusByUrl(url: String, tagFavStatus: TagFavStatus) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTagRecentStatusByUrl(url: String, maxRecentItems: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun clearRecentItems() {
        // Do nothing
    }
}