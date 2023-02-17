package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.data
import com.under9.shared.core.result.succeeded
import kotlinx.coroutines.CoroutineDispatcher

class GetLatestHeyStatusUseCase(private val ioDispatcher: CoroutineDispatcher,
                                private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository): UseCase<Unit, String?>(ioDispatcher) {

    // Empty value
    override suspend fun execute(parameters: Unit): String? {
        val result = chatFeedRepository.getLatestHeyStatus()
        return if (result.succeeded) {
            result.data!!.data!!.status!!.title
        } else {
            throw result.exceptionOrNull()!!
        }
    }
}