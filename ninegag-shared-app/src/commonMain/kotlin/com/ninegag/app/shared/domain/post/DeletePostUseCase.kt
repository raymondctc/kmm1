package com.ninegag.app.shared.domain.post

import com.ninegag.app.shared.data.post.PostRepository
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class DeletePostUseCase(
    private val postRepository: PostRepository,
    ioDispatcher: CoroutineDispatcher
): UseCase<String, Boolean>(ioDispatcher) {
    override suspend fun execute(parameters: String): Boolean {
        val result = postRepository.deletePost(parameters)
        return result.isSuccess()
    }
}