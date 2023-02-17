package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.api.model.ApiHeyChatAccept
import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.data
import kotlinx.coroutines.CoroutineDispatcher

class AcceptChatRequestUseCase(
        ioDispatcher: CoroutineDispatcher,
        private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository): UseCase<String, ApiHeyChatAccept.Chat>(ioDispatcher) {

    override suspend fun execute(requestId: String): ApiHeyChatAccept.Chat {
        val result = chatFeedRepository.acceptChatRequestResult(requestId)
        if (result.isSuccess()) {
            return result.data!!.data!!.chat!!
        } else {
            throw result.exceptionOrNull()!!
        }
    }
}