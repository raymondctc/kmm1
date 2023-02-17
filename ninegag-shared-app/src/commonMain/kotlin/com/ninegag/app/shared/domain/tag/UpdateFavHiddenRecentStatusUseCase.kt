package com.ninegag.app.shared.domain.tag

import com.ninegag.app.shared.data.tag.TagListRepository
import com.ninegag.app.shared.data.tag.model.TagFavStatus
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class UpdateFavHiddenRecentStatusUseCase(
    private val tagListRepository: TagListRepository,
    ioDispatcher: CoroutineDispatcher,
): UseCase<UpdateFavHiddenRecentStatusUseCase.Param, Unit>(ioDispatcher) {
    companion object {
        const val MAX_PERSIST_RECENT_ITEMS = 30
    }

    override suspend fun execute(parameters: Param) {
        when (parameters) {
            is UpdateFavHiddenStatusParam ->
                tagListRepository.updateTagFavOrHiddenStatusByUrl(parameters.url, parameters.tagFavStatus)
            is UpdateRecentSectionParam ->
                tagListRepository.updateTagRecentStatusByUrl(parameters.url, MAX_PERSIST_RECENT_ITEMS)
            is MigrateLegacyGroupFavHiddenStatusParam ->
                tagListRepository.updateTagFavOrHiddenStatusByUrl(parameters.url, parameters.favStatus)
            is MigrateLegacyGroupRecentStatusParam ->
                tagListRepository.updateTagRecentStatusByUrl(parameters.url, MAX_PERSIST_RECENT_ITEMS)
        }
    }

    sealed class Param
    data class UpdateFavHiddenStatusParam(
        val url: String,
        val tagFavStatus: TagFavStatus
    ): Param()

    data class UpdateRecentSectionParam(
        val url: String
    ): Param()

    data class MigrateLegacyGroupFavHiddenStatusParam(
        val url: String,
        val favStatus: TagFavStatus
    ): Param()

    data class MigrateLegacyGroupRecentStatusParam(
        val url: String
    ): Param()
}
