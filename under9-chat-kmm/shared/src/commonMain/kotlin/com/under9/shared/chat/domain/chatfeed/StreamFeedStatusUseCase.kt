package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.chat.data.user.HeyUserRepository
import com.under9.shared.chat.domain.model.HeyFeedListDomainModel
import com.under9.shared.core.FlowUseCase
import com.under9.shared.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class StreamFeedStatusUseCase(
    private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository,
    private val heyUserRepository: HeyUserRepository = RepositoryManager.heyUserRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope,
    private val callback: StreamStatusCallback? = null
): FlowUseCase<String, HeyStatusResultCommand>(ioDispatcher) {

    private val pool = HeyFeedStatusPool(chatFeedRepository, heyUserRepository, ioDispatcher, scope)

    /**
     * filterType: default, male, female
     * */
    override fun execute(filterType: String): Flow<Result<HeyStatusResultCommand>> {
        pool.currentFilter = filterType

        return pool.statuesFlow.map {
            Result.Success(it)
        }.onEach {
            callback?.collect(it.data)
        }.flowOn(ioDispatcher)
    }

    fun refreshList(filterType: String) {
        pool.currentFilter = filterType
        pool.refreshAndEmitList()
    }

    fun preparePendingStatus() {
        pool.popNewStatus()
    }
}

interface StreamStatusCallback {
    fun collect(command: HeyStatusResultCommand)
}

sealed class HeyStatusResultCommand {
    data class FetchedStatuses(val list: List<HeyFeedListDomainModel>) : HeyStatusResultCommand()
    data class PreparedPendingStatus(val item: HeyFeedListDomainModel) : HeyStatusResultCommand()
}

