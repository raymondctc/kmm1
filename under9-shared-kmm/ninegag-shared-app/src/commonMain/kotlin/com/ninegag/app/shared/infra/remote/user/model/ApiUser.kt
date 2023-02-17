package com.ninegag.app.shared.infra.remote.user.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.jvm.JvmField

@Serializable
data class ApiUser(
    var userId: String? = null,
    var userName: String? = null,
    var loginName: String? = null,
    var profileUrl: String? = null,
    var avatarUrlTiny: String? = null,
    var avatarUrlSmall: String? = null,
    var avatarUrlMedium: String? = null,
    var avatarUrlLarge: String? = null,
    var isActivePro: Int = 0,
    var isActiveProPlus: Int = 0,
    var isVerifiedAccount: Int = 0,
    var about: String? = null,
    var fullName: String? = null,
    var accountId: String? = null,
    var emojiStatus: String? = null,
    var website: String? = null,
    var permissions: JsonElement? = null,
    var profileColor: String? = null,
    /**
     * country => hometown
     */
    var country: String? = null,
    /**
     * current => location
     */
    var location: String? = null,
    var creationTs: Long = 0,
    var activeTs: Long = 0,
    var uploadTs: Long = 0,
    var preferences: ApiUserPrefs? = null,
    var membership: ApiMembership? = null) {

    companion object {
        const val KEY_AVATAR_URL_SMALL = "avatarUrlSmall"
        const val KEY_AVATAR_URL_MEDIUM = "avatarUrlMedium"
        const val KEY_AVATAR_URL_LARGE = "avatarUrlLarge"
    }
}

@Serializable
class ApiUserPrefs(
    @JvmField var hideProBadge: Int = 0,
    @JvmField var hideActiveTs: Int = 0,
    @JvmField var backgroundColor: String? = "",
    @JvmField var accentColor: String? = "", // which is equal to profile color set in user profile, FYI https://github.com/9gag/9gag-android/issues/3260
    @JvmField var onlineStatusMode: Int = UserPrefsState.OnlineIndicatorState.Show.value, // FYI: https://github.com/9gag/9gag-android/wiki/Online-indicator
    @JvmField var hideFromRobots: Int = 0,
    @JvmField var creatorUpdateStatus: Int? = null // null - show notice, 0/1 - no action for client
)

@Serializable
object UserPrefsState {
    enum class ProBadgeState(val value: Int) {
        Hide(1), Show(0);

        companion object {
            fun from(value: Int) = values().find{ it.value == value }
        }
    }

    enum class OnlineIndicatorState(val value: Int) {
        Show(1), Hide(2), Ninja(3);

        companion object {
            fun from(value: Int) = values().find{ it.value == value }
        }
    }

    enum class ActiveTsState(val value: Int) {
        Show(0), Hide(1);

        companion object {
            fun from(value: Int) = values().find{ it.value == value }
        }
    }
}

@Serializable
data class ApiMembership(
    @JvmField var productId: String = "",
    @JvmField var purchaseTs: Long = 0,
    @JvmField var subscription: ApiSubscription? = null
) {

    @Serializable
    data class ApiSubscription(
        @JvmField var expiryTs: Long = 0,
        @JvmField var isGracePeriod: Int = 0,
        @JvmField var isExpired: Int = 0
    )
}