package com.ninegag.app.shared.data.user

import com.ninegag.app.shared.data.auth.model.AccountSession
import com.ninegag.app.shared.data.user.RemoteUserDatasource.Companion.VALUE_COMMENT
import com.ninegag.app.shared.data.user.RemoteUserDatasource.Companion.VALUE_POST
import com.ninegag.app.shared.domain.GagHttpHeaderValueManager
import com.russhwolf.settings.Settings
import com.under9.shared.core.U9Json
import io.github.aakira.napier.Napier

interface LocalUserDatasource {
    companion object {
        const val KEY_BLOCKED_POSTS = "blocked_posts"
        const val KEY_BLOCKED_USERS = "blocked_users"
    }

    /**
     * Block Post
     * */
    fun isPostBlocked(postId: String): Boolean
    fun isPostOPCommentBlocked(postId: String): Boolean
    fun addBlockedPost(postId: String, type: RemoteUserDatasource.BlockPostType)
    fun storeBlockedPosts(posts: Map<String, String>)
    fun clearBlockedPosts()

    /**
     * Block User
     * */
    suspend fun addBlockedUser(accountId: String)
    suspend fun removeBlockedUser(accountId: String)
    fun isAccountBlocked(accountId: String): Boolean
    fun storeBlockedUsers(accountIds: List<String>)
    fun clearBlockedUsers()

    @Deprecated("TODO: For sample project only, don't call it else where")
    fun tmp_cacheAccountSession(accountSession: AccountSession)

    @Deprecated("Temp method for clearing token")
    fun resetUserToken()
}

internal class LocalUserDatasourceImpl(
    private val settings: Settings
): LocalUserDatasource {

    private val blockedAccountIds: HashSet<String> by lazy {
        produceBlockedAccountSet()
    }
    
    private val blockedPost: HashMap<String, String> by lazy {
        produceBlockedPosts()
    }

    /**
     * Block Post
     * */
    override fun isPostBlocked(postId: String): Boolean {
        return if (blockedPost.contains(postId)) {
            val type = blockedPost[postId]
            type == VALUE_POST
        } else {
            false
        }
    }

    override fun isPostOPCommentBlocked(postId: String): Boolean {
        return if (blockedPost.contains(postId)) {
            val type = blockedPost[postId]
            type == VALUE_COMMENT
        } else {
            false
        }
    }

    override fun addBlockedPost(postId: String, type: RemoteUserDatasource.BlockPostType) {
        updateLocalBlockPost {
            val typeStr = when(type) {
                RemoteUserDatasource.BlockPostType.POST -> VALUE_POST
                RemoteUserDatasource.BlockPostType.COMMENT -> VALUE_COMMENT
            }
            blockedPost[postId] = typeStr
        }
    }

    override fun storeBlockedPosts(posts: Map<String, String>) {
        blockedPost.putAll(posts)
        settings.putString(LocalUserDatasource.KEY_BLOCKED_POSTS, U9Json.encodeToString(posts))
    }

    override fun clearBlockedPosts() {
        Napier.d("clearBlockedPosts, blockedPosts=$blockedPost")
        blockedPost.clear()
        settings.putString(LocalUserDatasource.KEY_BLOCKED_POSTS, "")
    }

    private fun produceBlockedPosts(): HashMap<String, String> {
        val blockedPostStr = settings.getString(LocalUserDatasource.KEY_BLOCKED_POSTS, "")
        return if (blockedPostStr.isBlank()) {
            HashMap()
        } else {
            try {
                U9Json.decodeFromString(blockedPostStr)
            } catch (e: NumberFormatException) {
                Napier.e("error=", e)
                HashMap()
            }
        }
    }


    /**
     * Block User
     * */
    override suspend fun addBlockedUser(accountId: String) {
        updateLocalBlockedAccount {
            blockedAccountIds.add(accountId)
        }
    }

    override suspend fun removeBlockedUser(accountId: String) {
        updateLocalBlockedAccount {
            blockedAccountIds.remove(accountId)
        }
    }

    override fun isAccountBlocked(accountId: String): Boolean {
        return blockedAccountIds.contains(accountId)
    }

    override fun storeBlockedUsers(accountIds: List<String>) {
        accountIds.forEach {
            blockedAccountIds.add(it)
        }
        settings.putString(LocalUserDatasource.KEY_BLOCKED_USERS, U9Json.encodeToString(accountIds))
    }

    override fun clearBlockedUsers() {
        updateLocalBlockedAccount {
            blockedAccountIds.clear()
        }
    }

    private fun produceBlockedAccountSet(): HashSet<String> {
        val blockedAccountIdsStr = settings.getString(LocalUserDatasource.KEY_BLOCKED_USERS, "")

        return if (blockedAccountIdsStr.isEmpty()) {
            HashSet()
        } else {
            try {
                U9Json.decodeFromString(blockedAccountIdsStr)
            } catch (e: NumberFormatException) {
                Napier.e("error=", e)
                HashSet()
            }
        }
    }

    private fun updateLocalBlockedAccount(function: () -> Unit) {
        function()
        settings.putString(LocalUserDatasource.KEY_BLOCKED_USERS, U9Json.encodeToString(blockedAccountIds))
    }

    private fun updateLocalBlockPost(function: () -> Unit) {
        function()
        settings.putString(LocalUserDatasource.KEY_BLOCKED_POSTS, U9Json.encodeToString(blockedPost))
    }

    override fun tmp_cacheAccountSession(accountSession: AccountSession) {
        val (appToken, commentToken, notifToken, user) = accountSession

        appToken?.userToken?.let {
            settings.putString(GagHttpHeaderValueManager.KEY_NINE_GAG_TOKEN, it)
        }

        user?.let {
            val userMap = mapOf(
                "loginName" to (accountSession.user?.loginName ?: ""),
                "userId" to (accountSession.user?.userId ?: ""),
                "accountId" to (accountSession.user?.userId ?: ""),
            )
            settings.putString("user", U9Json.encodeToString(userMap))
        }
    }

    override fun resetUserToken() {
        settings.putString(GagHttpHeaderValueManager.KEY_NINE_GAG_TOKEN, "")
    }
}
