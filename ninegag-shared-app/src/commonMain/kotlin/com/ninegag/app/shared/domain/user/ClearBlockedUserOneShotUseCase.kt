package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.UserRepository

class ClearBlockedUserOneShotUseCase(
    private val userRepository: UserRepository
) {

    fun execute(): Boolean {
        userRepository.clearBlockedUsers()
        return true
    }
}