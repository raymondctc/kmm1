package com.ninegag.app.shared.user.model

data class AppUser(
    val id: Long? = null,
    val userId: String? = null,
    val accountId: String? = null,
    val username: String? = null,
    val avatarUrls: String? = null,
    val isActivePro: Int? = null,
    val isActiveProPlus: Int? = null,
    val fullName: String? = null,
    val profileUrl: String? = null,
    val about: String? = null,
    val emojiStatus: String? = null,
    val location: String? = null,
    val country: String? = null,
    val creationTs: Long? = null,
    val activeTs: Long? = null,
    val isVerifiedAccount: Int? = null
) {
    private val avatarMap: Map<String, String>? = null
}