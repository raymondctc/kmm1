package com.ninegag.app.shared.data.user

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.under9.shared.core.result.Result

interface UserRepository {
    /**
     * Block OP Post
     * */
    suspend fun blockPost(postId: String, type: RemoteUserDatasource.BlockPostType): Result<ApiBaseResponse>
    fun isPostBlocked(postId: String): Boolean
    fun isPostOPCommentBlocked(postId: String): Boolean
    fun addBlockedPostToCache(postId: String, type: RemoteUserDatasource.BlockPostType)
    fun storeBlockedPosts(posts: Map<String, String>)
    fun clearBlockedPosts()

    /**
     * Block User
     * */
    suspend fun blockUser(accountId: String): Result<ApiBaseResponse>
    suspend fun unblockUser(accountId: String): Result<ApiBaseResponse>
    suspend fun addBlockUserToCache(accountId: String)
    suspend fun removeBlockedUserFromCache(accountId: String)
    fun isAccountBlocked(accountId: String): Boolean
    fun storeBlockedUsers(accountIds: List<String>)
    fun clearBlockedUsers()
}

class UserRepositoryImpl(
    private val remoteUserDatasource: RemoteUserDatasource,
    private val localUserDatasource: LocalUserDatasource
): UserRepository {
    /**
     * Block Post
     * */
    override suspend fun blockPost(
        postId: String,
        type: RemoteUserDatasource.BlockPostType
    ): Result<ApiBaseResponse> {
        return remoteUserDatasource.blockPost(postId, type)
    }

    override fun isPostBlocked(postId: String) = localUserDatasource.isPostBlocked(postId)

    override fun isPostOPCommentBlocked(postId: String) = localUserDatasource.isPostOPCommentBlocked(postId)

    override fun addBlockedPostToCache(postId: String, type: RemoteUserDatasource.BlockPostType) = localUserDatasource.addBlockedPost(postId, type)

    override fun clearBlockedPosts() = localUserDatasource.clearBlockedPosts()

    /**
     * Block User
     * */
    override suspend fun blockUser(accountId: String): Result<ApiBaseResponse> = remoteUserDatasource.blockUser(accountId)

    override suspend fun unblockUser(accountId: String): Result<ApiBaseResponse> = remoteUserDatasource.unblockUser(accountId)

    override suspend fun addBlockUserToCache(accountId: String) = localUserDatasource.addBlockedUser(accountId)

    override suspend fun removeBlockedUserFromCache(accountId: String) = localUserDatasource.removeBlockedUser(accountId)

    override fun isAccountBlocked(accountId: String): Boolean = localUserDatasource.isAccountBlocked(accountId)

    override fun storeBlockedPosts(posts: Map<String, String>) = localUserDatasource.storeBlockedPosts(posts)

    override fun storeBlockedUsers(accountIds: List<String>) = localUserDatasource.storeBlockedUsers(accountIds)

    override fun clearBlockedUsers() {
        localUserDatasource.clearBlockedUsers()
    }
}