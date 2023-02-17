package com.under9.shared.chat.domain.user

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.user.HeyUserRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class CheckUserBlockedUseCase(
    private val heyUserRepository: HeyUserRepository = RepositoryManager.heyUserRepository,
    ioDispatcher: CoroutineDispatcher
): UseCase<String, Boolean>(ioDispatcher) {

    override suspend fun execute(userId: String): Boolean {
        val blockedIds = heyUserRepository.getAllBlockedUserIds()
        return blockedIds.contains(userId)
    }
}