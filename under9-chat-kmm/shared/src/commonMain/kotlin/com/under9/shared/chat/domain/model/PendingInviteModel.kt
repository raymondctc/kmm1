package com.under9.shared.chat.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PendingInviteModel(
        val list: List<Pair<Long, String>>
)