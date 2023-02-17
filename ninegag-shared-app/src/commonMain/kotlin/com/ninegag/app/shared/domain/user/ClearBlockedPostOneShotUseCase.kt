package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.UserRepository

class ClearBlockedPostOneShotUseCase(
    private val userRepository: UserRepository
) {

    fun execute(): Boolean {
        userRepository.clearBlockedPosts()
        return true
    }
}