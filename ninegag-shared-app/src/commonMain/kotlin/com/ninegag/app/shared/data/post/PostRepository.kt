package com.ninegag.app.shared.data.post

import com.ninegag.app.shared.infra.remote.base.ApiBaseResponse
import com.ninegag.app.shared.infra.remote.post.model.ApiGag
import com.ninegag.app.shared.infra.remote.post.model.ApiPostsResponse
import com.ninegag.app.shared.post.model.PostListModel
import com.ninegag.app.shared.post.model.PostModel
import com.under9.shared.core.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface PostRepository {
    suspend fun deletePost(postId: String): Result<ApiBaseResponse>
    fun getRemoteRelatedPosts(postId: String, after: String?): Flow<Result<PostListModel>>
}

class PostRepositoryImpl(
    private val remotePostDatasource: RemotePostDatasource
): PostRepository {
    override suspend fun deletePost(postId: String) = remotePostDatasource.deletePost(postId)

    override fun getRemoteRelatedPosts(postId: String, after: String?): Flow<Result<PostListModel>> {
        return flow {
            val result = remotePostDatasource.getRemoteRelatedPosts(postId, after)
            if (result.isSuccess()) {
                if (result.getOrNull() != null) {
                    val listModel = mapToPostListModel(result.getOrNull()!!.data)
                    emit(Result.Success(listModel))
                }
            }
        }
    }

    private fun mapToPostListModel(data: ApiPostsResponse.Data): PostListModel = PostListModel(
            posts = data.posts?.map { mapToPostModel(it) } ?: mutableListOf(),
            after = data.after
        )

    private fun mapToPostModel(data: ApiGag): PostModel = PostModel(
            postId = data.id,
            title = data.title,
            type = data.type,
            description = data.description,
            commentOpClientId = data.commentOpClientId,
            commentOpSignature = data.commentOpSignature,
            commentsCount = data.commentsCount,
            upVoteCount = data.upVoteCount,
            downVoteCount = data.downVoteCount,
            nsfw = data.nsfw,
            version = data.version,
            hasLongPostCover = data.hasLongPostCover,
            hasImageTile = data.hasImageTile,
            userScore = data.userScore,
            albumWebUrl = data.albumWebUrl,
            sourceDomain = data.sourceDomain,
            sourceUrl = data.sourceUrl,
            isVoteMasked = data.isVoteMasked,
            creationTs = data.creationTs,
            postSection = data.postSection,
            media = data.images,
            gagTile = data.postTile,
            creator = data.creator,
            targetedAdTags = data.targetedAdTags,
            url = data.url,
            isAnonymous = data.isAnonymous,
            postVideo = data.postVideo,
            article = data.article,
            tags = data.tags,
            comment = data.comment,
            promoted = data.promoted,
            orderId = data.orderId,
            postUser = data.postUser
        )
}