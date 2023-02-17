package com.ninegag.app.shared.domain.tag

import com.ninegag.app.shared.data.tag.TagListRepository
import com.ninegag.app.shared.data.tag.model.Tag
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class StoreHistoryTagUseCase(
    private val tagListRepository: TagListRepository,
    ioDispatcher: CoroutineDispatcher
): UseCase<StoreHistoryTagUseCase.Param, Boolean>(ioDispatcher) {

    override suspend fun execute(parameters: Param): Boolean {
        // Store the most recent tag
        val reversedList = parameters.tags.reversed()
        tagListRepository.refreshAndStoreHistoryTag(reversedList, parameters.keepAmount)
        return true
    }

    data class Param(
        val tags: List<Tag>,
        val keepAmount: Int
    )
}