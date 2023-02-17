package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.RemoteUserDatasource
import com.ninegag.app.shared.data.user.UserRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class BlockPostOneShotUseCase(
    private val userRepository: UserRepository,
    ioDispatcher: CoroutineDispatcher
): UseCase<BlockPostOneShotUseCase.Param, Boolean>(ioDispatcher) {
    override suspend fun execute(parameters: Param): Boolean {
        val result = userRepository.blockPost(parameters.postId, parameters.type)
        if (result.isSuccess()) {
            userRepository.addBlockedPostToCache(parameters.postId, parameters.type)
        }
        return result.isSuccess()
    }

    data class Param(val postId: String,
                     val type: RemoteUserDatasource.BlockPostType)
}