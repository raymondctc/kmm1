package com.under9.shared.chat.domain.notification

data class ChatPushNotiDataModel(
    val requestId: String,
    val channelId: String,
    val title: String,
    val message: String,
    val type: Int
) {
    companion object {
        const val TYPE_CHAT = 0
        const val TYPE_INVITE = 1
    }
}


