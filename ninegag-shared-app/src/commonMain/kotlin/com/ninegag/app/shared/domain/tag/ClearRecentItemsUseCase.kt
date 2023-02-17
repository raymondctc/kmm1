package com.ninegag.app.shared.domain.tag

import com.ninegag.app.shared.data.tag.TagListRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class ClearRecentItemsUseCase(
    private val tagListRepository: TagListRepository,
    ioDispatcher: CoroutineDispatcher,
): UseCase<Unit, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: Unit) {
        tagListRepository.clearRecentItems()
    }
}