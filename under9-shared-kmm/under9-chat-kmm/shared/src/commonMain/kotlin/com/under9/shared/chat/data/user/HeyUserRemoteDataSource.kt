package com.under9.shared.chat.data.user

import com.under9.shared.chat.api.ApiServiceManager
import com.under9.shared.chat.api.basemodel.ApiBaseResponse
import com.under9.shared.core.result.Result

class HeyUserRemoteDataSource(private val apiServiceManager: ApiServiceManager = ApiServiceManager) {

    suspend fun subscribeFcmNotification(fcmToken: String): Result<ApiBaseResponse> {
        return apiServiceManager.subscribeFcmNotification(fcmToken)
    }

    suspend fun unsubscribeFcmNotification(fcmToken: String): Result<ApiBaseResponse> {
        return apiServiceManager.unsubscribeFcmNotification(fcmToken)
    }
}