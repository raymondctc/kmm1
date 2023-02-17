package com.under9.shared.chat.domain.user

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.user.HeyUserRepository
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher

class SubscribeFcmNotifUseCase(
    private val heyUserRepository: HeyUserRepository = RepositoryManager.heyUserRepository,
    ioDispatcher: CoroutineDispatcher
): UseCase<String, Unit>(ioDispatcher) {

    override suspend fun execute(fcmToken: String) {
        val result = heyUserRepository.subscribeFcmNotification(fcmToken)
        if (result.isSuccess()) {
            Result.Success(Unit)
        } else {
            result as Result.Error
        }
    }
}