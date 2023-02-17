package com.under9.shared.chat.data.chatfeed

import com.under9.shared.db.HeyFeedListEntity
import com.under9.shared.db.HeyStatusEntity
import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import com.under9.shared.chat.api.model.*
import com.under9.shared.core.result.Result

import kotlinx.coroutines.flow.Flow

class ChatFeedRepository(
    private val localDatasource: ChatFeedLocalDatasource,
    private val remoteDatasource: ChatFeedRemoteDatasource
) {

    suspend fun getHeyFeedOneShortResult(filterType: String): Result<ApiHeyFeedResponse> {
        return remoteDatasource.getHeyFeed(filterType)
    }

    suspend fun getHeyHometownFeedResult(filterType: String): Result<ApiHeyFeedResponse> {
        return remoteDatasource.getHeyHometownFeed()
    }

    fun observeFeedList(): Flow<List<HeyFeedListEntity>> {
        return localDatasource.observeFeedList()
    }

    fun updateOrInsertFeedListItemToCache(list: List<ApiHeyStatus>) {
        localDatasource.updateOrInsertFeedListItemToCache(list)
    }

    fun getHeyStatuesResult(ids: List<String>): Result<List<HeyStatusEntity>> {
        return localDatasource.getHeyStatuses(ids)
    }

    fun getHeyStatusesInIdsExcludeBlockUsers(statusIds: List<String>, blockedUserIds: List<String>): Result<List<HeyStatusEntity>> {
        return localDatasource.getHeyStatusesInIdsExcludeBlockUsers(statusIds, blockedUserIds)
    }

    suspend fun updateHeyStateResult(msg: String): Result<ApiHeyStatusResponse> {
        return remoteDatasource.updateHeyStatus(msg)
    }

    suspend fun sendChatRequestResult(statusId: String): Result<ApiHeyChatRequest> {
        return remoteDatasource.sendChatRequestResult(statusId)
    }

    suspend fun acceptChatRequestResult(requestId: String): Result<ApiHeyChatAccept> {
        return remoteDatasource.acceptChatRequestResult(requestId)
    }

    suspend fun leaveChannelRequestResult(channelId: String): Result<ApiBaseResponse> {
        return remoteDatasource.leaveChannelRequestResult(channelId)
    }

    suspend fun getLatestHeyStatus(): Result<ApiHeyStatusResponse> {
        return remoteDatasource.getLatestHeyStatus()
    }
}