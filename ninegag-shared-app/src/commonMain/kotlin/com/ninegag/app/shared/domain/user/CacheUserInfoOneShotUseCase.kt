package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.UserRepository
import io.github.aakira.napier.Napier

class CacheUserInfoOneShotUseCase(private val userRepository: UserRepository) {
    fun execute(param: Param) {
        with(userRepository) {
            Napier.d("storeBlockedUsers=${param.accountIds}, ${param.posts}")
            param.accountIds?.let { storeBlockedUsers(it) }
            param.posts?.let { storeBlockedPosts(it) }
        }
    }

    data class Param(
        val accountIds: List<String>?,
        val posts: Map<String, String>?
    )
}