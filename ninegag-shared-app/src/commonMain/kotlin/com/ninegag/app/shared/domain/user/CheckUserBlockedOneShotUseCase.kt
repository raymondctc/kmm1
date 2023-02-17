package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.UserRepository
import io.github.aakira.napier.Napier

class CheckUserBlockedOneShotUseCase(private val userRepository: UserRepository) {
    fun execute(accountId: String): Boolean {
        val isBlocked = userRepository.isAccountBlocked(accountId)
        return isBlocked
    }
}