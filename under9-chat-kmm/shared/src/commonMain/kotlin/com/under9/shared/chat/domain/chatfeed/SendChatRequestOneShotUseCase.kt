package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.chat.domain.model.HeyChatRequestDomainModel
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.data
import com.under9.shared.core.result.succeeded
import kotlinx.coroutines.CoroutineDispatcher

class SendChatRequestOneShotUseCase(
        private val repository: ChatFeedRepository = RepositoryManager.chatFeedRepository,
        ioDispatcher: CoroutineDispatcher
) : UseCase<String, HeyChatRequestDomainModel>(ioDispatcher) {

    override suspend fun execute(statusId: String): HeyChatRequestDomainModel {
        val result = repository.sendChatRequestResult(statusId)
        return if (result.succeeded) {
            val requestResponse = result.data!!.data!!.request!!
            with(requestResponse) {
                HeyChatRequestDomainModel(
                        id = id,
                        timestamp = timestamp,
                        ttl = ttl,
                        requestStatus = requestStatus,
                        acceptStatus = acceptStatus
                )
            }
        } else {
            throw result.exceptionOrNull()!!
        }
    }
}