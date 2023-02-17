package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.core.FlowUseCase
import com.under9.shared.core.result.Result
import com.under9.shared.core.util.FlowUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@OptIn(kotlin.time.ExperimentalTime::class)
class InviteControlUseCase(
    private val ioDispatcher: CoroutineDispatcher
) : FlowUseCase<Int, Boolean>(ioDispatcher) {

    /**
     * @param parameters is ttl in millisecond
     * */
    override fun execute(parameters: Int): Flow<Result<Boolean>> {
        // -2500 is the buffer time switch from io thread to main thread
        return FlowUtil.delayFlow((parameters).toDuration(DurationUnit.MILLISECONDS)).map {
            Result.Success(true)
        }.flowOn(ioDispatcher)
    }
}