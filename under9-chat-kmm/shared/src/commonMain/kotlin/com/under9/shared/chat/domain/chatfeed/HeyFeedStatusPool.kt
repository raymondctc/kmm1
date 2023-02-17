package com.under9.shared.chat.domain.chatfeed

import com.under9.shared.chat.data.RepositoryManager
import com.under9.shared.chat.data.chatfeed.ChatFeedRemoteDatasource
import com.under9.shared.chat.data.chatfeed.ChatFeedRemoteDatasource.Companion.FILTER_TYPE_HOMETOWN
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.chat.data.user.HeyUserRepository
import com.under9.shared.chat.domain.model.HeyFeedListDomainModel
import com.under9.shared.chat.util.Gender
import com.under9.shared.core.result.data
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.jvm.Synchronized

class HeyFeedStatusPool(
    private val chatFeedRepository: ChatFeedRepository = RepositoryManager.chatFeedRepository,
    private val heyUserRepository: HeyUserRepository = RepositoryManager.heyUserRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope
) {
    private val pool = ArrayDeque<HeyFeedListDomainModel>()
    private val poolStatusIds = HashSet<String>()
    private val emittedStatusIds = HashSet<String>()
    
    var currentFilter = FILTER_TYPE_HOMETOWN

    // https://medium.com/mobile-app-development-publication/mutex-for-coroutines-5f4a4ca60763
    private val mutex = Mutex()

    private val statusNumCountForEachFetch = 9
    private val preloadIndex = 3

    private val _statuesFlow = MutableSharedFlow<HeyStatusResultCommand>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val statuesFlow: SharedFlow<HeyStatusResultCommand> = _statuesFlow
    private val latestEmittedList = ArrayList<HeyFeedListDomainModel>()
    private var lastPreparedStatus: HeyFeedListDomainModel? = null

    private val femaleUserMap: ArrayList<HeyFeedListDomainModel> = ArrayList()
    private val maleUserMap: ArrayList<HeyFeedListDomainModel> = ArrayList()
    
    private val promoFemaleUser = false

    init {
        refreshAndEmitList()
    }

    @Synchronized
    private suspend fun fetchAndAddToPool() {
        Napier.d("fetchStatus")

        // load chat feed already filtered blocked user
        val usecase = LoadChatFeedOneShotUseCase(chatFeedRepository, heyUserRepository, ioDispatcher)
        val result = usecase.invoke(currentFilter)
        if (result.isSuccess()) {
            val apiStatues = result.data!!
            apiStatues.forEach {
                // filter the statuses already on the list
                if (!poolStatusIds.contains(it.id)) {
                    Napier.d("Added to the pool=${it.id}")
                    poolStatusIds.add(it.id)
                    pool.add(it)
                    if (it.gender == Gender.WOMAN) {
                        femaleUserMap.add(it)
                    } else {
                        maleUserMap.add(it)
                    }
                }
            }
            pool.shuffle()
        } else {
            Napier.e("error=", result.exceptionOrNull())
        }
    }

    @Synchronized
    fun refreshAndEmitList() {
        scope.launch {
            mutex.withLock {
                fetchAndAddToPool()
                val list = if (promoFemaleUser) {
                    producePromoFemaleList()
                } else {
                    produceList()
                }
                _statuesFlow.tryEmit(HeyStatusResultCommand.FetchedStatuses(list))
            }
        }
    }

    private fun produceList(): List<HeyFeedListDomainModel> {
        val pendingStatuses = ArrayList<HeyFeedListDomainModel>()
        val curPendingStatusIds = HashSet<String>()
        val domainFilterType = when (currentFilter) {
            ChatFeedRemoteDatasource.FILTER_TYPE_FEMALE -> Gender.WOMAN
            ChatFeedRemoteDatasource.FILTER_TYPE_MALE -> Gender.MAN
            else -> Gender.DEFAULT_ALL
        }
        val blockedUserIds = heyUserRepository.getAllBlockedUserIds()

        run loop@{
            pool.forEach {
                if (!emittedStatusIds.contains(it.id) &&
                    (domainFilterType == Gender.DEFAULT_ALL || it.gender == domainFilterType) &&
                    !blockedUserIds.contains(it.userId)
                ) {
                    emittedStatusIds.add(it.id)
                    pendingStatuses.add(it)
                    curPendingStatusIds.add(it.id)
                    if (pendingStatuses.size >= statusNumCountForEachFetch) {
                        return@loop
                    }
                }
            }
        }

        // If all emitted item are the same with the pool
        // Which mean user see all statues, so we can show the previous items
        if (pendingStatuses.size < statusNumCountForEachFetch && pool.size > 0) {
            var remainItemToAdd = statusNumCountForEachFetch - pendingStatuses.size

            pool.shuffle()
            run loop@{
                pool.forEach {
                    if (remainItemToAdd > 0 &&
                        !curPendingStatusIds.contains(it.id) &&
                        (domainFilterType == Gender.DEFAULT_ALL || it.gender == domainFilterType) &&
                        !blockedUserIds.contains(it.userId)
                    ) {
                        pendingStatuses.add(it)
                        --remainItemToAdd
                    }
                }
            }
        }

        Napier.d("Fetch Items=${pendingStatuses.size}")

        latestEmittedList.clear()
        latestEmittedList.addAll(pendingStatuses)

        return pendingStatuses
    }

    private fun producePromoFemaleList(): List<HeyFeedListDomainModel> {
        val pendingStatuses = ArrayList<HeyFeedListDomainModel>()
        val addedFemaleStatusIds = HashSet<String>()
        val addedMaleStatusIds = HashSet<String>()
        val targetFemaleNumber = (4..5).random()
        Napier.d("targetFemaleNumber=$targetFemaleNumber")

        val blockedUserIds = heyUserRepository.getAllBlockedUserIds()

        run loop@{
            pool.forEach {
                if (!emittedStatusIds.contains(it.id) &&
                    !blockedUserIds.contains(it.userId)
                ) {
                    if ((addedFemaleStatusIds.size < targetFemaleNumber && it.gender == Gender.WOMAN) ||
                        (addedMaleStatusIds.size < statusNumCountForEachFetch - targetFemaleNumber) && it.gender == Gender.MAN) {
                        emittedStatusIds.add(it.id)
                        pendingStatuses.add(it)
                        if (it.gender == Gender.WOMAN) {
                            addedFemaleStatusIds.add(it.id)
                            Napier.d("Added female status=${it.title}, show before = false")
                        } else {
                            addedMaleStatusIds.add(it.id)
                        }
                    }

                    if (pendingStatuses.size >= statusNumCountForEachFetch) {
                        return@loop
                    }
                }
            }
        }

        // If all emitted item are the same with the pool
        // Which mean user see all statues, so we can show the previous items
        if (pendingStatuses.size < statusNumCountForEachFetch && pool.size > 0) {

            // Try to fetch female user not show before but in the pool
            var remainFemaleItemToAdd = targetFemaleNumber - addedFemaleStatusIds.size
            if (remainFemaleItemToAdd > 0) {
                femaleUserMap.shuffle()
                run loop@{
                    femaleUserMap.forEach {
                        if (remainFemaleItemToAdd > 0 &&
                            !addedFemaleStatusIds.contains(it.id)
                        ) {
                            addedFemaleStatusIds.add(it.id)
                            pendingStatuses.add(it)
                            --remainFemaleItemToAdd
                        } else {
                            if (remainFemaleItemToAdd <= 0) {
                                return@loop
                            }
                        }
                    }
                }
            }

            // if added users from the map but still not able to achieve [statusNumCountForEachFetch]
            var remainItemToAdd = statusNumCountForEachFetch - pendingStatuses.size
            if (remainItemToAdd > 0) {
                pool.shuffle()
                run loop@{
                    pool.forEach {
                        if (remainItemToAdd > 0 &&
                            !addedFemaleStatusIds.contains(it.id) &&
                            !(addedFemaleStatusIds.size >= targetFemaleNumber && it.gender == Gender.WOMAN)  && // exclude female status as previously already added
                            !addedMaleStatusIds.contains(it.id) &&
                            !blockedUserIds.contains(it.userId)
                        ) {
                            pendingStatuses.add(it)
                            if (it.gender == Gender.WOMAN) {
                                addedFemaleStatusIds.add(it.id)
                                Napier.d("Added female status=${it.title}, show before = true")
                            } else {
                                addedMaleStatusIds.add(it.id)
                            }
                            --remainItemToAdd
                        } else {
                            if (remainItemToAdd <= 0) {
                                return@loop
                            }
                        }
                    }
                }
            }
        }

        Napier.d("Fetch Items=${pendingStatuses.size}")

        latestEmittedList.clear()
        latestEmittedList.addAll(pendingStatuses)

        pendingStatuses.shuffle()

        return pendingStatuses
    }

    @Synchronized
    fun popNewStatus() {
        scope.launch {
            mutex.withLock {
                val domainFilterType = when (currentFilter) {
                    ChatFeedRemoteDatasource.FILTER_TYPE_FEMALE -> Gender.WOMAN
                    ChatFeedRemoteDatasource.FILTER_TYPE_MALE -> Gender.MAN
                    else -> Gender.DEFAULT_ALL
                }
                val blockedUserIds = heyUserRepository.getAllBlockedUserIds()

                var pendingStatus: HeyFeedListDomainModel? = null
                // just pop 1 item
                run loop@{
                    pool.forEach {
                        if (!emittedStatusIds.contains(it.id) &&
                            (domainFilterType == Gender.DEFAULT_ALL || it.gender == domainFilterType)
                        ) {
                            emittedStatusIds.add(it.id)
                            pendingStatus = it
                            return@loop
                        }
                    }
                }

                // Already seen all items
                if (pendingStatus == null) {
                    pool.shuffle()

                    run shufflePool@{
                        pool.forEach { targetStatus ->
                            var isSuitable = true

                            run loop@{
                                latestEmittedList.forEach {
                                    // if find the random status which is currently showing on screen,
                                    // find a new one
                                    if (targetStatus.id == it.id) {
                                        Napier.d("Found duplicated which showing on screen, next try")
                                        isSuitable = false
                                        return@loop
                                    }
                                }
                            }

                            if (isSuitable && !(domainFilterType == Gender.DEFAULT_ALL || targetStatus.gender == domainFilterType)) {
                                Napier.d("Gender Not match")
                                isSuitable = false
                            }

                            if (isSuitable && lastPreparedStatus != null && lastPreparedStatus!!.id == targetStatus.id) {
                                Napier.d("Found duplicated which in from the latest hey, next try")
                                isSuitable = false
                            }

                            if (isSuitable && blockedUserIds.contains(targetStatus.id)) {
                                Napier.d("Blocked user, next try")
                                isSuitable = false
                            }

                            if (isSuitable) {
                                Napier.d("Matched with a pending status not showing on screen")
                                pendingStatus = targetStatus
                                lastPreparedStatus = targetStatus
                                return@shufflePool
                            }
                        }
                    }
                }

                // should contain 0/1 item
                if (pendingStatus != null) {
                    Napier.d("pendingStatus=${pendingStatus!!.title}")
                    _statuesFlow.tryEmit(HeyStatusResultCommand.PreparedPendingStatus(pendingStatus!!))
                }

                // preload:
                // fetch status each time even when there don't have new status,
                // Assume the goal is to discover as many statuses as possible, so may consider to fetch
                // trigger fetch for each pop command
                if (pool.size - emittedStatusIds.size <= preloadIndex) {
                    fetchAndAddToPool()
                }
            }
        }
    }
}