package com.ninegag.app.shared.domain.tag

import com.ninegag.app.shared.data.tag.TagListRepository
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.under9.shared.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import com.under9.shared.core.result.Result
import kotlinx.coroutines.flow.take

class FetchNavTagListUseCase(
    private val tagListRepository: TagListRepository,
    ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<FetchNavTagListUseCase.Param, TagListModel>(ioDispatcher) {

    companion object {
        const val MAX_SHOWN_RECENT_ITEMS = 3
    }

    override fun execute(parameters: Param): Flow<Result<TagListModel>> {
        return when (parameters) {
            is NavTagListParam -> tagListRepository.getNavTagList(MAX_SHOWN_RECENT_ITEMS)
            is CustomizeHomePageListParam -> tagListRepository.getCustomizeHomePageTagList()
            is HomeTagListParam -> tagListRepository.getHomeTagList()
            is TrendingTagListParam -> tagListRepository.getTrendTagList()
                .take(parameters.count)
            is HiddenTagListParam -> tagListRepository.getHiddenTagList(parameters.limit)
            is FavTagListParam -> tagListRepository.getFavTagList(parameters.limit)
            is RecentTagListParam -> tagListRepository.getRecentTagList(parameters.limit)
        }
    }

    sealed class Param
    object NavTagListParam: Param()
    object CustomizeHomePageListParam: Param()
    object HomeTagListParam: Param()
    data class TrendingTagListParam(val count: Int): Param()
    data class HiddenTagListParam(val limit: Long = 100): Param()
    data class FavTagListParam(val limit: Long = Long.MAX_VALUE): Param()
    data class RecentTagListParam(val limit: Long = MAX_SHOWN_RECENT_ITEMS.toLong()): Param()
}
