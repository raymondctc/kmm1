package com.under9.shared.chat.domain.auth

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.signin.HeyAccountRepository
import com.under9.shared.chat.util.ChatTokenValueManager
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.data
import com.under9.shared.core.result.succeeded
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher

class ForceRefreshUserUseCase(
    private val heyAccountRepository: HeyAccountRepository = RepositoryManager.heyAccountRepository,
    private val tokenValueManager: ChatTokenValueManager,
    ioDispatcher: CoroutineDispatcher
) : UseCase<String, HeyAccountDomainModel>(ioDispatcher) {

    override suspend fun execute(gagHeyUserAccessToken: String): HeyAccountDomainModel {
        // If null, check from remote
        val apiResult = heyAccountRepository.loginHeyUserResult(gagHeyUserAccessToken)
        if (apiResult.isSuccess()) {
            val response = apiResult.data!!.data!!

            // store to local
            heyAccountRepository.updateOrInsertHeyAccountToCache(
                response.user,
                response.chat,
                response.userToken,
                response.tokenExpiry,
                response.secondsTillExpiry,
                gagHeyUserAccessToken
            )

            with(tokenValueManager) {
                setToken(response.userToken)
                setTokenExpiryTs(response.tokenExpiry)
                setSecondsTillExpiry(response.secondsTillExpiry)
            }
            val newHeyAccountDomainModel = getHeyAccountDomainModelFromCache(gagHeyUserAccessToken)
            if (newHeyAccountDomainModel != null) {
                return newHeyAccountDomainModel
            } else {
                throw NullPointerException("No user")
            }
        } else {
            Napier.e("error=", apiResult.exceptionOrNull())
            throw IllegalArgumentException(apiResult.getErrorMsg())
        }
    }

    private fun getHeyAccountDomainModelFromCache(gagHeyUserAccessToken: String): HeyAccountDomainModel? {
        val accountResult = heyAccountRepository.getLocalHeyAccountFromAccessTokenResult(gagHeyUserAccessToken)
        return if (accountResult.succeeded) {
            val entity = accountResult.data!!
            HeyAccountDomainModel(
                userId = entity.user_id,
                notificationTopic = entity.notification_topic,
                userToken = entity.user_token,
                gender = entity.gender,
                hometown = entity.hometown,
                tokenExpiry = entity.token_expiry,
                streamUserId = entity.stream_user_id,
                secondTillExpiry = entity.seconds_till_expiry,
                hasChat = entity.has_chat ?: false,
            )
        } else {
            null
        }
    }
}