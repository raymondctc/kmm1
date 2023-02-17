package com.under9.shared.chat.domain.auth

import com.under9.shared.analytics.Analytics
import com.under9.shared.chat.data.ObjectManager
import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.signin.HeyAccountRepository
import com.under9.shared.chat.util.ChatTokenValueManager
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.data
import com.under9.shared.core.result.succeeded
import com.under9.shared.core.util.PlatformUtils
import com.under9.shared.infra.network.TokenValueManager
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher

class LoginHeyUserOneShotUseCase(
    private val heyAccountRepository: HeyAccountRepository = RepositoryManager.heyAccountRepository,
    private val tokenValueManager: ChatTokenValueManager,
    private val ioDispatcher: CoroutineDispatcher,
    private val analytics: Analytics
) : UseCase<String, HeyAccountDomainModel>(ioDispatcher) {

    override suspend fun execute(gagHeyUserAccessToken: String): HeyAccountDomainModel {
        val heyAccountDomainModel = getHeyAccountDomainModelFromCache(gagHeyUserAccessToken)
        // Check from cache
        return if (heyAccountDomainModel != null && heyAccountDomainModel.hometown.isNotBlank()) {
            with(tokenValueManager) {
                setToken(heyAccountDomainModel.userToken)
                setTokenExpiryTs(heyAccountDomainModel.tokenExpiry)
                setSecondsTillExpiry(heyAccountDomainModel.secondTillExpiry)
            }
            heyAccountDomainModel
        } else {
            // If null, check from remote
            ForceRefreshUserUseCase(heyAccountRepository, tokenValueManager, ioDispatcher).invoke(gagHeyUserAccessToken).data!!
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
