package com.ninegag.app.shared.domain.tag

import com.ninegag.app.shared.data.tag.TagListRepository
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.under9.shared.core.FlowUseCase
import com.under9.shared.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take

class FetchTrendingTagListUseCase(
    private val tagListRepository: TagListRepository,
    ioDispatcher: CoroutineDispatcher,
): FlowUseCase<FetchTrendingTagListUseCase.Param, TagListModel>(ioDispatcher) {

    override fun execute(parameters: Param): Flow<Result<TagListModel>> {
        return tagListRepository.getTrendTagList()
            .take(parameters.count)
    }

    data class Param(val count: Int)

    companion object {
        const val MAX_AMOUNT = 5
    }
}