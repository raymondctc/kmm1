package com.under9.shared.chat.util

object RLogMessage {
    object Category {
        const val API = "HEY_API"

        /**
         * getstream's API
         */
        const val STREAM_API = "STREAM_API"

        const val HEY_AUTH = "HEY_AUTH"
        const val STREAM_AUTH = "STREAM_AUTH"
    }

    object Key {
        const val API_RENEW_TOKEN = "RENEW_TOKEN"
        const val STREAM_API_CONNECT = "STREAM_CONNECT"
        const val STREAM_ADD_DEVICE = "STREAM_ADD_DEVICE"
        const val STREAM_FETCH_CHANNEL = "STREAM_FETCH_CHANNEL"
        const val STREAM_LOAD_TOKEN = "STREAM_LOAD_TOKEN"
        const val STREAM_QUERY_CHANNEL = "STREAM_QUERY_CHANNEL"
        const val HEY_LOGIN = "HEY_LOGIN"
        const val STREAM_AUTH_LOGIN = "STREAM_AUTH_LOGIN"
    }
}