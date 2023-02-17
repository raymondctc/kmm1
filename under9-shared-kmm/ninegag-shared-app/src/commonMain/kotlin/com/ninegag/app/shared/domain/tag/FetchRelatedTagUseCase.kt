package com.ninegag.app.shared.domain.tag

import com.ninegag.app.shared.data.tag.TagListRepository
import com.ninegag.app.shared.data.tag.model.Tag
import com.under9.shared.core.FlowUseCase
import com.under9.shared.core.UseCase
import com.under9.shared.core.result.Result
import com.under9.shared.core.result.data
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take

class FetchRelatedTagUseCase(
    private val tagListRepository: TagListRepository,
    ioDispatcher: CoroutineDispatcher
): FlowUseCase<FetchRelatedTagUseCase.Param, List<Tag>>(ioDispatcher) {

    private val maxItem = 10

    override fun execute(parameters: Param): Flow<Result<List<Tag>>> {
        return tagListRepository.getRelatedTags(parameters.tag).take(maxItem)
    }
    
    data class Param(
        val tag: String
    )
}
