package com.ninegag.app.shared.domain.user

import com.ninegag.app.shared.data.user.RemoteUserDatasource
import com.ninegag.app.shared.data.user.UserRepository

class CheckHidePostOneShotUseCase(private val userRepository: UserRepository) {
    fun execute(param: Param): Boolean {
        return when(param.type) {
            RemoteUserDatasource.BlockPostType.COMMENT -> userRepository.isPostOPCommentBlocked(param.postId)
            RemoteUserDatasource.BlockPostType.POST -> userRepository.isPostBlocked(param.postId)
        }
    }

    data class Param(
        val postId: String,
        val type: RemoteUserDatasource.BlockPostType
    )
}