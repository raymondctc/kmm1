package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.UserRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

// Boolean
class ManageBlockUserOneShotUseCase(
    private val userRepository: UserRepository,
    ioDispatcher: CoroutineDispatcher
) : UseCase<ManageBlockUserOneShotUseCase.Param, Boolean>(ioDispatcher) {

    override suspend fun execute(param: Param): Boolean {
        val accountId = param.accountId
        with(userRepository) {
            return if (param.isBlock) {
                addBlockUserToCache(accountId)
                val result = blockUser(accountId)
                if (!result.isSuccess()) {
                    removeBlockedUserFromCache(accountId)
                }

                result.isSuccess()
            } else {
                removeBlockedUserFromCache(accountId)
                val result = unblockUser(accountId)
                if (!result.isSuccess()) {
                    addBlockUserToCache(accountId)
                }

                result.isSuccess()
            }
        }
    }
    data class Param(
        val accountId: String,
        val isBlock: Boolean
    )
}
