package com.ninegag.app.shared.domain.post

import com.ninegag.app.shared.data.post.PostRepository
import com.ninegag.app.shared.post.model.PostListModel
import com.under9.shared.core.FlowUseCase
import com.under9.shared.core.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class FetchRemoteRelatedPostUseCase(
    private val postRepository: PostRepository,
    ioDispatcher: CoroutineDispatcher): FlowUseCase<FetchRemoteRelatedPostUseCase.Param, PostListModel>(ioDispatcher) {

    data class Param(
        val postId: String,
        val after: String? = null
    )

    override fun execute(parameters: Param): Flow<Result<PostListModel>> {
        return postRepository.getRemoteRelatedPosts(parameters.postId, parameters.after)
    }
}