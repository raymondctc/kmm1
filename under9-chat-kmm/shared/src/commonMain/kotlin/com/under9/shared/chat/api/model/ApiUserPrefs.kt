package com.under9.shared.chat.api.model

import com.under9.shared.chat.api.HeyUserPrefsState
import kotlinx.serialization.Serializable

@Serializable
data class ApiUserPrefs(
    var hideProBadge: Int = 0,
    var hideActiveTs: Int = 0,
    var backgroundColor: String? = "",
    var accentColor: String? = "", // which is equal to profile color set in user profile, FYI https://github.com/9gag/9gag-android/issues/3260
    var onlineStatusMode: Int = HeyUserPrefsState.SHOW_ONLINE_INDICATOR, // FYI: https://github.com/9gag/9gag-android/wiki/Online-indicator
    var hideFromRobots: Int = 0
)