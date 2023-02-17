package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.db.HeyStatusEntity
import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRemoteDatasource
import com.under9.shared.core.UseCase
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.chat.data.user.HeyUserRepository
import com.under9.shared.chat.domain.model.HeyFeedListDomainModel
import com.under9.shared.core.result.data
import com.under9.shared.core.result.succeeded
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher

class LoadChatFeedOneShotUseCase(
    private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository,
    private val heyUserRepository: HeyUserRepository = RepositoryManager.heyUserRepository,
    ioDispatcher: CoroutineDispatcher
) : UseCase<String, List<HeyFeedListDomainModel>>(ioDispatcher) {

    /**
     * filter type, includes: "default", "male", "female", "hometown"
     * */
    override suspend fun execute(filterType: String): List<HeyFeedListDomainModel> {
        val heyStatusDomainModelList = ArrayList<HeyFeedListDomainModel>()

        val result = if(filterType == ChatFeedRemoteDatasource.FILTER_TYPE_HOMETOWN) {
            chatFeedRepository.getHeyHometownFeedResult(filterType)
        } else {
            chatFeedRepository.getHeyFeedOneShortResult(filterType)
        }
        if (result.succeeded && result.data!!.data != null) {

            val apiHeyStatusList = result.data!!.data!!.statuses
            val apiHeyStatuesList = ArrayList(apiHeyStatusList.toCollection(ArrayList()))

            // Store the result from remote to local
            chatFeedRepository.updateOrInsertFeedListItemToCache(apiHeyStatuesList)

            val ids = ArrayList<String>()
            val blockedUserIds = heyUserRepository.getAllBlockedUserIds()

            apiHeyStatusList.forEach {
                Napier.d("fetched: ${it.title}")
                ids.add(it.id)
            }

            // Loop the entities from DB
            val statusEntitiesResult = chatFeedRepository.getHeyStatusesInIdsExcludeBlockUsers(ids, blockedUserIds)
            if (statusEntitiesResult.succeeded) {
                val statusEntities = statusEntitiesResult.data!!
                // map to domain models
                (statusEntities as MutableList<HeyStatusEntity>).forEach {
                    heyStatusDomainModelList.add(
                        HeyFeedListDomainModel(
                            id = it.id,
                            title = it.title,
                            userId = it.user_id,
                            gender = it.gender,
                            hometown = it.hometown,
                            timestamp = it.timestamp,
                        )
                    )
                }
            }
            heyStatusDomainModelList.shuffle()
            return heyStatusDomainModelList
        } else {
            Napier.e("error=", result.exceptionOrNull())
            throw IllegalStateException(result.exceptionOrNull())
        }
    }
}