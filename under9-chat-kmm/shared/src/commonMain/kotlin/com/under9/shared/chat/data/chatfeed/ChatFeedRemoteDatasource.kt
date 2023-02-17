package com.under9.shared.chat.data.chatfeed

import com.under9.shared.chat.api.ApiServiceManager
import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import com.under9.shared.chat.api.model.*
import com.under9.shared.core.result.Result

class ChatFeedRemoteDatasource(private val apiServiceManager: ApiServiceManager = ApiServiceManager) {

    companion object {

        const val FILTER_TYPE_DEFAULT = "default"
        @Deprecated("Use hometown for hey v2")
        const val FILTER_TYPE_MALE = "male"
        @Deprecated("Use hometown for hey v2")
        const val FILTER_TYPE_FEMALE = "female"
        const val FILTER_TYPE_HOMETOWN = "hometown"
    }

    suspend fun getHeyFeed(filterType: String): Result<ApiHeyFeedResponse> {
        return apiServiceManager.getRandomHeyFeed(filterType)
    }

    suspend fun getHeyHometownFeed(): Result<ApiHeyFeedResponse> {
        return apiServiceManager.getHometownCountryFeed()
    }

    suspend fun updateHeyStatus(msg: String): Result<ApiHeyStatusResponse> {
        return apiServiceManager.updateHeyStatus(msg)
    }

    suspend fun sendChatRequestResult(statusId: String): Result<ApiHeyChatRequest> {
        return apiServiceManager.sendHeyChatRequest(statusId)
    }

    suspend fun acceptChatRequestResult(requestId: String): Result<ApiHeyChatAccept> {
        return apiServiceManager.acceptChatRequest(requestId)
    }

    suspend fun leaveChannelRequestResult(channelId: String): Result<ApiBaseResponse> {
        return apiServiceManager.leaveChat(channelId)
    }

    suspend fun getLatestHeyStatus(): Result<ApiHeyStatusResponse> {
        return apiServiceManager.getLatestHeyStatus()
    }
}