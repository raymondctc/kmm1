package com.under9.shared.chat.data.user

import com.under9.shared.chat.data.ObjectManager
import com.under9.shared.chat.db.ChatDatabase
import kotlinx.coroutines.CoroutineDispatcher

class HeyUserLocalDataSource(
    private val database: ChatDatabase = ObjectManager.chatDatabase,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbQueries = database.blockedHeyStatusEntityQueries

    fun getAllBlockedUserIds(): List<String> {
        val ids = ArrayList<String>()

        val entities = dbQueries.getAllBlockedUsers().executeAsList()
        entities.forEach {
            ids.add(it.user_id)
        }
        return ids
    }

    fun insertBlockedUserId(userId: String) {
        dbQueries.insertBlockedUsers(
            id = null,
            user_id = userId
        )
    }
}