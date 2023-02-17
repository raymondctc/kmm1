package com.under9.shared.chat.util

object Global {
//    const val DEBUG = true
    const val CMTY_STAGING_BASE_URL = "https://api.9chat.org"
    const val CMTY_PRODUCTION_BASE_URL = "https://api.chats.org"

    const val CHAT_MAX_QUOTA = 6
    const val HEY_QUOTE_MAX_LENGTH = 140
    const val HEY_QUOTE_WARNING_LENGTH = 40

    const val BROADCAST_HEY_CHAT_REQUEST = "com.under9.shared.chat.util.hey_chat_request_broadcast"
    const val KEY_FCM_TYPE = "hey_fcm_type"
    const val KEY_FCM_VALUE = "hey_fcm_value"

    const val VALUE_CHAT_REQUEST = "CHAT_REQUEST"
    const val VALUE_CHAT_ACCEPT = "CHAT_ACCEPT"

    const val KEY_DEEPLINK_TAB_POS = "tab_pos"
    const val VALUE_TAB_CHATTING = "tab_chatting"

    const val INVITE_REQUEST_TTL = 5 * 1000
    const val ACCEPT_REQUEST_TTL = 30 * 1000
    const val SHOW_HEY_BOTTOM_NAV_BAR = false

    const val MAX_CONCURRENT_INVITE_COUNT = 3
}