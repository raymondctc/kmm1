package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.succeeded
import kotlinx.coroutines.CoroutineDispatcher

class LeaveChannelUseCase(
        ioDispatcher: CoroutineDispatcher,
        private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository
): UseCase<String, Boolean>(ioDispatcher) {

    override suspend fun execute(channelId: String): Boolean {
        val result = chatFeedRepository.leaveChannelRequestResult(channelId)
        if (result.isSuccess()) {
            return result.succeeded
        } else {
            throw result.exceptionOrNull()!!
        }
    }
}