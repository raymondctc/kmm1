package com.under9.shared.chat.data.chatfeed

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.under9.shared.db.HeyFeedListEntity
import com.under9.shared.db.HeyStatusEntity
import com.under9.shared.chat.api.model.ApiHeyStatus
import com.under9.shared.chat.data.ObjectManager
import com.under9.shared.chat.db.ChatDatabase
import com.under9.shared.chat.domain.model.HeyFeedListDomainModel
import com.under9.shared.infra.db.transactionWithContext
import com.under9.shared.core.result.Result
import com.under9.shared.core.result.succeeded
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ChatFeedLocalDatasource(
    private val database: ChatDatabase = ObjectManager.chatDatabase,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbQueries = database.heyStatusEntityQueries

    fun observeFeedList(): Flow<List<HeyFeedListEntity>> {
        return database
            .heyFeedListEntityQueries
            .selectAll()
            .asFlow()
            .mapToList()
            .flowOn(backgroundDispatcher)
    }

    fun updateOrInsertFeedListItemToCache(list: List<ApiHeyStatus>) {
        list.forEach { apiHeyStatus ->
            val statusEntity = getHeyStatus(apiHeyStatus.id)
            with(apiHeyStatus) {
                if (statusEntity.succeeded) {
                    dbQueries.updateHeyStatusByApi(
                        user_id = userId,
                        gender = gender,
                        hometown = hometown,
                        title = title,
                        timestamp = timestamp,
                        id = id
                    )
                } else {
                    dbQueries.insertHeyStatus(
                        id = id,
                        user_id = userId,
                        gender = gender,
                        hometown = hometown,
                        title = title,
                        timestamp = timestamp,
                        is_blocked = false,
                        is_reported = false,
                    )
                }
            }
        }
    }

    fun getHeyStatus(id: String): Result<HeyStatusEntity> {
        return try {
            val status = dbQueries.getHeyStateById(id).executeAsOneOrNull()
            if (status == null) {
                Result.Error(NullPointerException("no this hey status"))
            } else {
                Result.Success(status)
            }
        } catch (e: Exception) {
            Result.Error(NullPointerException("error hey status, e=$e"))
        }
    }

    fun getHeyStatuses(ids: List<String>): Result<List<HeyStatusEntity>> {
        return try {
            val statuses = dbQueries.getHeyStatusesInIds(ids).executeAsList()
            Result.Success(statuses)
        } catch (e: Exception) {
            Result.Error(NullPointerException("error hey status, e=$e"))
        }
    }

    fun getHeyStatusesInIdsExcludeBlockUsers(statusIds: List<String>, blockedUserIds: List<String>): Result<List<HeyStatusEntity>> {
        return try {
            val statuses = dbQueries.getHeyStatusesInIdsExcludeBlockUsers(statusIds, blockedUserIds).executeAsList()
            Result.Success(statuses)
        } catch (e: Exception) {
            Result.Error(NullPointerException("error hey status, e=$e"))
        }
    }
}
