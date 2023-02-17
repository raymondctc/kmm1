package com.ninegag.app.shared.domain.post

import com.ninegag.app.shared.data.post.PostFacade
import com.under9.shared.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class SubmitPostWithLinkUseCase(
    private val postFacade: PostFacade,
    ioDispatcher: CoroutineDispatcher
): UseCase<SubmitPostWithLinkUseCase.Param, Boolean>(ioDispatcher) {
    override suspend fun execute(parameters: Param): Boolean {
        val result = postFacade.submitPostWithLink(parameters.uuid, parameters.url)
        return result.isSuccess()
    }

    data class Param(
        val uuid: String,
        val url: String
    )
}