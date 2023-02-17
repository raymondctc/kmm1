package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher

class UpdateHeyStatusOneShotUseCase(
    private val ioDispatcher: CoroutineDispatcher,
    private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository
): UseCase<String, Result<Unit>>(ioDispatcher) {

    override suspend fun execute(msg: String): Result<Unit> {
        val apiHeyStatus = chatFeedRepository.updateHeyStateResult(msg)
        Napier.d("apiHeyStatus=$apiHeyStatus")
        return if (apiHeyStatus.isSuccess()) {
            Result.Success(Unit)
        } else {
            throw apiHeyStatus.exceptionOrNull()!!
        }
    }
}