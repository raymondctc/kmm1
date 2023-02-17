package com.under9.shared.chat.domain.model

data class HeyFeedListDomainModel(
    val id: String,
    val title: String,
    val userId: String,
    val gender: String,
    val hometown: String,
    val timestamp: Long,
    val isInvite: Boolean = false
)