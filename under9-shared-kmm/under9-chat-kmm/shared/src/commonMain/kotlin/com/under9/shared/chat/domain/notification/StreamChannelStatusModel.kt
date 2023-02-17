package com.under9.shared.chat.domain.notification

data class StreamChannelStatusModel(
        @Deprecated("Use hometown for hey v2")
        val gender: String,
        val hometown: String,
        val id: String,
        val title: String,
        val userId: String
)