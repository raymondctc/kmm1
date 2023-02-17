package com.under9.shared.chat.data

import com.under9.shared.chat.data.chatfeed.ChatFeedLocalDatasource
import com.under9.shared.chat.data.chatfeed.ChatFeedRemoteDatasource
import com.under9.shared.chat.data.chatfeed.ChatFeedRepository
import com.under9.shared.chat.data.signin.HeyAccountRepository
import com.under9.shared.chat.data.signin.RemoteLoginUserDataSource
import com.under9.shared.chat.data.user.HeyUserLocalDataSource
import com.under9.shared.chat.data.user.HeyUserRemoteDataSource
import com.under9.shared.chat.data.user.HeyUserRepository

object RepositoryManager {

    val chatFeedRepository: ChatFeedRepository by lazy {
        ChatFeedRepository(
            getChatFeedLocalDataSource(),
            getRemoteChatFeedDataSource()
        )
    }

    val heyAccountRepository: HeyAccountRepository by lazy {
        HeyAccountRepository(
            getRemoteLoginUserDataSource()
        )
    }

    val heyUserRepository: HeyUserRepository by lazy {
        HeyUserRepository(
            getHeyUserLocalDataSource(),
            getHeyUserRemoteDataSource()
        )
    }

    private fun getChatFeedLocalDataSource(): ChatFeedLocalDatasource {
        return ChatFeedLocalDatasource(ObjectManager.chatDatabase, ObjectManager.backgroundDispatcher)
    }

    private fun getRemoteLoginUserDataSource(): RemoteLoginUserDataSource {
        return RemoteLoginUserDataSource()
    }

    private fun getRemoteChatFeedDataSource(): ChatFeedRemoteDatasource {
        return ChatFeedRemoteDatasource()
    }

    private fun getHeyUserLocalDataSource(): HeyUserLocalDataSource {
        return HeyUserLocalDataSource(ObjectManager.chatDatabase, ObjectManager.backgroundDispatcher)
    }

    private fun getHeyUserRemoteDataSource(): HeyUserRemoteDataSource {
        return HeyUserRemoteDataSource()
    }
}