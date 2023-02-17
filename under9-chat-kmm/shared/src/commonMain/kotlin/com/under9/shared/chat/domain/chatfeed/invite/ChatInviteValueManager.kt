package com.under9.shared.chat.domain.chatfeed.invite

import com.under9.shared.chat.api.model.ApiHeyChatRequest
import com.under9.shared.chat.data.ObjectManager
import com.under9.shared.chat.domain.model.HeyChatRequestDomainModel
import com.under9.shared.chat.domain.model.PendingInviteModel
import com.under9.shared.chat.util.Global
import com.under9.shared.core.U9Json
import com.under9.shared.core.util.PlatformUtils
import com.under9.shared.infra.db.getString
import com.under9.shared.infra.db.putString
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.math.abs
import kotlin.math.min

class ChatInviteValueManager() {
    private val preferences = ObjectManager.preferences

    companion object {
        const val KEY_PENDING_INVITE = "pending_invite"
    }

    fun addPendingChat(modelStr: String) {
        val encodedPendingInvite = preferences.getString(KEY_PENDING_INVITE, "")

        val list = if (encodedPendingInvite.isBlank()) {
            ArrayList()
        } else {
            val model = U9Json.decodeFromString<PendingInviteModel>(encodedPendingInvite)
            model.list as ArrayList<Pair<Long, String>>
        }
        val nowTs = PlatformUtils.currentTimeMillis / 1000
        list.add(Pair(nowTs, modelStr))

        val str = Json.encodeToString(PendingInviteModel(list))
        preferences.putString(KEY_PENDING_INVITE, str)
    }

    /**
     * 1. Get all pending invite and filter the expired invite
     * 2. Clean up pending invite in the preference store
     */
    fun getPendingInviteChat(): List<HeyChatRequestDomainModel>? {
        val encodedPendingInvite = preferences.getString(KEY_PENDING_INVITE, "")
        if (encodedPendingInvite.isBlank()) return null

        val model = U9Json.decodeFromString<PendingInviteModel>(encodedPendingInvite)
        val list = ArrayList<HeyChatRequestDomainModel>()

        val defaultTTL = Global.ACCEPT_REQUEST_TTL / 1000

        // 3 sec buffer
        val bufferForAppOpen = 3
        val now = PlatformUtils.currentTimeMillis / 1000

        // Pair<RequesterId, title>
        val addedPendingRequesterIdTitleMap = HashMap<String, MutableSet<String>>()

        val pendingList = model.list

        // Make sure show the latest invite first
        pendingList.reversed()

        pendingList.forEach {
            val receiveRequestTs = it.first + bufferForAppOpen
            val apiChatRequest = U9Json.decodeFromString<ApiHeyChatRequest.Request>(it.second)
            val diff = abs(now - receiveRequestTs)
            Napier.d("now=$now, receiveRequestTs=$receiveRequestTs")
            Napier.d("diff=$diff, ttl for domain = ${min(defaultTTL.toUInt(), diff.toUInt()).toInt()}")

            var availableToAdd = true
            val requesterId = apiChatRequest.requestStatus!!.userId
            val isRequesterIdAdded = addedPendingRequesterIdTitleMap[requesterId] != null
            if (isRequesterIdAdded) {
                // check is the same hey
                val curRequesterStatus = apiChatRequest.requestStatus!!.title
                val requesterStatusSet = addedPendingRequesterIdTitleMap[requesterId]
                // same user requesting same status
                if (requesterStatusSet!!.contains(curRequesterStatus)) {
                    availableToAdd = false
                }
            }

            if (availableToAdd) {
                val titleSet = HashSet<String>()
                addedPendingRequesterIdTitleMap[requesterId]?.let { it1 -> titleSet.addAll(it1) }
                titleSet.add(apiChatRequest.requestStatus!!.title)
                addedPendingRequesterIdTitleMap[requesterId] = titleSet

                list.add(HeyChatRequestDomainModel(
                        id = apiChatRequest.id,
                        timestamp = apiChatRequest.timestamp + bufferForAppOpen, // buffer
                        ttl = defaultTTL,
                        requestStatus = apiChatRequest.requestStatus,
                        acceptStatus = apiChatRequest.acceptStatus)
                )
            }
        }
        // reset
        preferences.putString(KEY_PENDING_INVITE, "")
        return list
    }


}