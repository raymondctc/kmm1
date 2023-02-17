package com.under9.shared.chat.analytics

object _MixpanelInternal {
    object Reason {
        const val Key = "Reason"
        val Values = _Values

        object _Values {
            const val InappropriateMessages = "Inappropriate messages"
            const val Spamming = "Spamming"
            const val IllegalActivities = "Illegal activities"
        }
    }

    object ReportPosition {
        const val Key = "Report Position"
        val Values = _Values

        object _Values {
            const val List = "List"
            const val Chat = "Chat"
        }
    }
}

object MixpanelEvent {
    object HeyApp {

        object Visits {
            const val KEY = "Hey Visits"

            const val ENTRY = "Hey Entry"
            const val DRAWER = "Drawer"
            const val TOOLBAR = "Toolbar"
            const val BOTTOM_NAVIGATION = "Bottom Navigation"
            const val INVITE_NOTIF = "Invite Notification"
            const val ACCEPT_NOTIF = "Accept Notification"
            const val CONVERSATION_NOTIF = "Conversation Notification"
        }
        const val StatusSet = "Hey Status Set"
        const val Quit = "Hey Quit"

        const val HeyStatus = "Hey Status"
        const val HeyStatusLength = "Hey Status Length"
    }

    object Onboard {
        const val Started = "Hey Onboard Started"
        const val HometownSet = "Hey Hometown Set"
        const val Completed = "Hey Onboard Completed"
        const val LaterClicked = "Hey Onboard Later Clicked"
        const val StatusSet = "Hey Onboard Status Set"
    }

    object Report {
        const val ChatReported = "Hey Chat Reported"
        val Reason = _MixpanelInternal.Reason
        val ReportPosition = _MixpanelInternal.ReportPosition

        const val Spammer = "Spammer"
        const val Sender = "Sender"
        const val Recipient = "Recipient"
        const val RequestId = "Request ID"
        const val SenderHometown = "Sender Hometown"
        const val RecipientHometown = "Recipient Hometown"
        const val HeyStatus = "Hey Status"
        const val TotalMessages = "Total Messages"
        const val LastMessage = "Last Message"
    }

    object Request {
        const val RequestSent = "Hey Request Sent"
        const val RequestAccepted = "Hey Request Accepted"
        const val RequestBeingAccepted = "Hey Request Being Accepted"

        const val Sender = "Sender"
        const val Recipient = "Recipient"
        const val RequestId = "Request ID"
        const val ChatId = "Chat ID"
        const val SenderHometown = "Sender Hometown"
        const val RecipientHometown = "Recipient Hometown"
        const val HeyStatus = "Hey Status"
        const val TotalMessages = "Total Messages"
        const val LastMessage = "Last Message"
    }

    object Message {
        const val FirstMessageSent = "Hey First Message Sent"
        const val ChatLeft = "Hey Chat Left"

        const val ChatId = "Chat ID"
        const val FirstMessageSender = "First Message Sender"
        const val Sender = "Sender"
        const val Recipient = "Recipient"
        const val RequestId = "Request ID"
        const val SenderHometown = "Sender Hometown"
        const val RecipientHometown = "Recipient Hometown"
        const val HeyStatus = "Hey Status"
        const val TotalMessages = "Total Messages"
        const val LastMessage = "Last Message"
    }
}