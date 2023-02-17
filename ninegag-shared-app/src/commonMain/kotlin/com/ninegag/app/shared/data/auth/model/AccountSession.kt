package com.ninegag.app.shared.data.auth.model

import com.ninegag.app.shared.data.user.model.AppUser

data class AccountSession(
    val appToken: AppToken?,
    val commentToken: CommentToken?,
    val notifToken: NotifToken?,
    val user: AppUser?
) {
    val hasToken = appToken != null
    val isGuest = user == null

    data class AppToken(
        val userToken: String,
        val expiryTs: Int,
        val secondsTillExpiry: Int
    )

    data class CommentToken(
        val type: String,
        val authToken: String,
        val expiryTs: Int
    )

    data class NotifToken(
        val readStateParams: String
    )
}