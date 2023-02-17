package com.under9.shared.chat.data.signin

import com.under9.shared.chat.api.model.*
import com.under9.shared.db.HeyAccountEntity
import com.under9.shared.chat.data.ObjectManager
import com.under9.shared.chat.db.ChatDatabase
import com.under9.shared.core.result.Result
import com.under9.shared.core.result.succeeded

class HeyAccountRepository(
    private val remoteLoginUserDataSource: RemoteLoginUserDataSource,
    private val dbRef: ChatDatabase = ObjectManager.chatDatabase
) {
    private val dbQueries = dbRef.heyAccountEntityQueries

//    fun loginHeyUserFlowResult(userToken: String): Flow<Result<ApiLoginResponse>> {
//        return remoteLoginUserDataSource.loginHeyUser(userToken)
//    }

    suspend fun loginHeyUserResult(gagHeyUserAccessToken: String): Result<ApiLoginResponse> {
        return remoteLoginUserDataSource.loginHeyUser(gagHeyUserAccessToken)
    }

    suspend fun getRemoteChatTokenResult(): Result<ApiHeyChatToken> {
        return remoteLoginUserDataSource.getChatToken()
    }

    fun updateOrInsertHeyAccountToCache(
        apiHeyAccount: ApiHeyAccount,
        apiChat: ApiLoginResponse.ApiChat,
        userToken: String,
        tokenExpiry: Long,
        secondsTillExpiry: Long,
        gagHeyUserAccessToken: String
    ) {
        val result = getLocalHeyAccountFromUserIdResult(apiHeyAccount.userId)
        with(apiHeyAccount) {
            if (result.succeeded) {
                dbQueries.updateHeyAccount(
                    gender = gender,
                    hometown = hometown,
                    user_token = userToken,
                    token_expiry = tokenExpiry,
                    notification_topic = notification.topic,
                    stream_user_id = apiChat.chatUserId,
                    has_chat = apiChat.hasChat,
                    user_id = userId,
                    gag_hey_user_access_token = gagHeyUserAccessToken,
                    seconds_till_expiry = secondsTillExpiry
                )
            } else {
                dbQueries.insertHeyAccount(
                    user_id = userId,
                    user_token = userToken,
                    token_expiry = tokenExpiry,
                    gender = gender,
                    hometown = hometown,
                    notification_topic = notification.topic,
                    stream_user_id = apiChat.chatUserId,
                    has_chat = apiChat.hasChat,
                    gag_hey_user_access_token = gagHeyUserAccessToken,
                    seconds_till_expiry = secondsTillExpiry
                )
            }
        }
    }

    fun getLocalHeyAccountFromUserIdResult(userId: String): Result<HeyAccountEntity> {
        return try {
            val users = dbQueries.getHeyAccountFromUserId(userId).executeAsList()
            if (users.isNotEmpty()) {
                Result.Success(users[0])
            } else {
                Result.Error(NullPointerException("no hey account"))
            }
        } catch (e: Exception) {
            Result.Error(NullPointerException("error hey account, e=$e"))
        }
    }

    fun getLocalHeyAccountFromAccessTokenResult(gagHeyUserAccessToken: String): Result<HeyAccountEntity> {
        return try {
            val users = dbQueries.getHeyAccountFromAccessToken(gagHeyUserAccessToken).executeAsList()
            if (users.isNotEmpty()) {
                Result.Success(users[0])
            } else {
                Result.Error(NullPointerException("no hey account"))
            }
        } catch (e: Exception) {
            Result.Error(NullPointerException("error hey account, e=$e"))
        }
    }

    @Deprecated("Use hometown for hey v2")
    suspend fun setHeyGenderResult(gender: String): Result<ApiHeyGender> {
        return remoteLoginUserDataSource.setGenderResult(gender)
    }

    suspend fun setHeyHometownResult(hometown: String): Result<ApiHeyHometown> {
        return remoteLoginUserDataSource.setHometownResult(hometown)
    }
}