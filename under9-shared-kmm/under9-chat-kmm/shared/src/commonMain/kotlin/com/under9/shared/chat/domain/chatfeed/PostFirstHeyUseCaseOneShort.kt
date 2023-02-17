package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.chat.data.signin.HeyAccountRepository
import com.under9.shared.core.UseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import com.under9.shared.core.result.Result

/**
 * Specific for the first time to post the hey,
 * including set the hey gender and upload the hey status
 *
 * Note that we need to update user gender set first before updating status
 * Otherwise it won’t show in the feed
 * */
class PostFirstHeyUseCaseOneShort(
    private val ioDispatcher: CoroutineDispatcher,
    private val heyAccountRepository: HeyAccountRepository = RepositoryManager.heyAccountRepository,
    private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository
): UseCase<FirstHeyUseCaseModel, Result<Unit>>(ioDispatcher) {

    override suspend fun execute(parameters: FirstHeyUseCaseModel): Result<Unit> {
        val (_, selectedHometown, heyQuoteMsg) = parameters
        Napier.d("Set hometown =${selectedHometown}")
        if (!selectedHometown.isNullOrBlank()) {
            val apiHeyResult = heyAccountRepository.setHeyHometownResult(selectedHometown)
            val apiHeyStatus = chatFeedRepository.updateHeyStateResult(heyQuoteMsg)
            return if (apiHeyResult.isSuccess() && apiHeyStatus.isSuccess()) {
                Result.Success(Unit)
            } else {
                throw IllegalStateException(apiHeyResult.getErrorMsg() + apiHeyStatus.getErrorMsg())
            }
        } else {
            throw IllegalStateException("Howntown is empty string")
        }
    }
}

// Either selected gender or hometown
data class FirstHeyUseCaseModel(
    @Deprecated("Clean Up")
    val selectedGender: String?,
    val selectedHometown: String?,
    val heyQuoteMsg: String
)