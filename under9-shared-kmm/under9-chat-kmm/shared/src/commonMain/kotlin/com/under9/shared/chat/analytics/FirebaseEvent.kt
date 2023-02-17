package com.under9.shared.chat.analytics

object FirebaseEvent {

    object Main {
        const val RefreshChannel = "RefreshChannel"
        const val TapEditQuote = "TapEditQuote"
        const val TapChannel = "TapChannel"
        const val TapSendRequest = "TapSendRequest"
        const val TapSetStatus = "TapSetStatus"
        const val TapAcceptRequest = "TapAcceptRequest"
        const val WaitTillExpired = "WaitTillExpired"
    }

    /**
     * 1-on-1 chat
     */
    object Message {
        const val TapSendChat = "Message_TapSendChat"
        const val TapLeaveChat = "Message_TapLeaveChat"
        const val TapReportChat = "Message_TapReportChat"
    }

    /**
     *
     */
    object OnBoard {
        const val TapStartChat = "TapStartChat"
        const val TapSetGender = "TapSetGender"
        const val TapSetFirstStatus = "TapSetFirstStatus"
    }
}

object FirebaseProperties {
    object All {
        const val AcceptStatusProperty = "confirmStatus"
        const val StatusAccepted = "Accepted"
        const val StatusCancelled = "Cancelled"
    }

    object Main {
        const val ChannelIdProperty = "id"

        /**
         * Where the chat request is accepted
         */
        const val AcceptedChatRequestFrom = "from"
        const val AcceptedFromFeedList = "channel"
        const val AcceptedFromPush = "push"
    }

    object OnBoard {
        const val GenderProperty = "gender"
        const val GenderValueMale = "M"
        const val GenderValueFemale = "F"
    }

    object Message {
        const val ChannelIdProperty = "id"
        const val UserIdProperty = "userId"
    }

    object Report {
        const val ReportReasonProperty = "reportReason"
    }
}