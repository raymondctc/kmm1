package com.under9.shared.chat.data.user

import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import com.under9.shared.core.result.Result

class HeyUserRepository(
    private val localDataSource: HeyUserLocalDataSource,
    private val remoteDataSource: HeyUserRemoteDataSource
) {
    fun getAllBlockedUserIds(): List<String> {
        return localDataSource.getAllBlockedUserIds()
    }

    fun insertBlockedUser(userId: String) {
        localDataSource.insertBlockedUserId(userId)
    }

    suspend fun subscribeFcmNotification(fcmToken: String): Result<ApiBaseResponse> {
        return remoteDataSource.subscribeFcmNotification(fcmToken)
    }

    suspend fun unsubscribeFcmNotification(fcmToken: String): Result<ApiBaseResponse> {
        return remoteDataSource.unsubscribeFcmNotification(fcmToken)
    }
}