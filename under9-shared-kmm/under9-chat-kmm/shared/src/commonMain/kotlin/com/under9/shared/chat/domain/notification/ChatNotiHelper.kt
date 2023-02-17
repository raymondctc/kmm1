package com.under9.shared.chat.domain.notification

import com.under9.shared.chat.domain.notification.ChatPushNotiDataModel.Companion.TYPE_CHAT
import io.github.aakira.napier.Napier

object ChatNotiHelper {

    fun chatEventToPushNotiModel(
        acceptStatus: StreamChannelStatusModel,
        requestStatus: StreamChannelStatusModel,
        curUserId: String,
        channelId: String,
        message: String
    ): ChatPushNotiDataModel {
        val opponentStatus = if (acceptStatus.userId == curUserId) {
            requestStatus
        } else {
            acceptStatus
        }

        Napier.i("chatEventToPushNotiModel, acceptStatus=$acceptStatus, requestStatus=$requestStatus")

        return ChatPushNotiDataModel(
            requestId = "",
            channelId = channelId,
            title = opponentStatus.title,
            message = message,
            type = TYPE_CHAT
        )
    }
}