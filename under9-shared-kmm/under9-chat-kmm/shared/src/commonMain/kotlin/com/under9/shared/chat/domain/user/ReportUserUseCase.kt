package com.under9.shared.chat.domain.user

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.user.HeyUserRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import com.under9.shared.core.result.Result

class ReportUserUseCase(
    private val heyUserRepository: HeyUserRepository = RepositoryManager.heyUserRepository,
    ioDispatcher: CoroutineDispatcher
) : UseCase<String, Result<Unit>>(ioDispatcher) {

    override suspend fun execute(userId: String): Result<Unit> {
        heyUserRepository.insertBlockedUser(userId)
        return Result.Success(Unit)
    }
}