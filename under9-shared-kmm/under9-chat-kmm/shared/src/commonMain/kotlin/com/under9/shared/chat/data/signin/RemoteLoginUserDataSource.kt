package com.under9.shared.chat.data.signin

import com.under9.shared.chat.api.ApiServiceManager
import com.under9.shared.chat.api.model.ApiHeyChatToken
import com.under9.shared.chat.api.model.ApiHeyGender
import com.under9.shared.chat.api.model.ApiHeyHometown
import com.under9.shared.chat.api.model.ApiLoginResponse
import com.under9.shared.core.result.Result

class RemoteLoginUserDataSource(private val apiServiceManager: ApiServiceManager = ApiServiceManager) {

    suspend fun loginHeyUser(gagHeyUserAccessToken: String): Result<ApiLoginResponse> {
        return apiServiceManager.loginAuthHey(gagHeyUserAccessToken)
    }

    @Deprecated("Use hometown for hey v2")
    suspend fun setGenderResult(gender: String): Result<ApiHeyGender> {
        return apiServiceManager.setHeyGender(gender)
    }

    suspend fun setHometownResult(hometown: String): Result<ApiHeyHometown> {
        return apiServiceManager.setHometown(hometown)
    }

    suspend fun getChatToken(): Result<ApiHeyChatToken> {
        return apiServiceManager.getChatToken()
    }
}