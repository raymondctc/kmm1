package com.ninegag.app.shared.analytics

/**
 * Check the spec in https://app.clickup.com/5700128/docs/5dyh0-91620/5dyh0-38840
 *
 * Put in alphabetical order
 */
object UtmConstant {

    object Key {
        const val UtmSource = "utm_source"
        const val UtmMedium = "utm_medium"
        const val UtmCampaign = "utm_campaign"
        const val UtmTerm = "utm_term"
        const val UtmContent = "utm_content"
    }

    /**
     * utm_source
     */
    object Source {
        const val Airdrop = "Airdrop" // iOS only
        const val CopyLink = "copy_link"
        const val Discord = "Discord"
        const val Email = "email"
        const val Facebook = "Facebook"
        const val FacebookMessenger = "Facebook_Messenger"
        const val GoogleMessaging = "Google_Messaging"
        const val Gmail = "Gmail"
        const val Instagram = "Instagram"
        const val Message = "Message" // iOS only
        const val Notes = "Notes" // iOS only
        const val Other = "other"
        const val Pinterest = "Pinterest"
        const val Safari = "Safari" // iOS only
        const val SamsungMessaging = "Samsung_Messaging"
        const val SignalMessaging = "Signal_Messaging"
        const val Snapchat = "Snapchat"
        const val Telegram = "Telegram"
        const val TelegramX = "Telegram_X"
        const val Twitter = "Twitter"
        const val Viber = "Viber"
        const val Whatsapp = "Whatsapp"
    }

    /**
     * utm_medium
     */
    object Medium {
        const val CommentShare = "comment_share"
        const val PostShare = "post_share"
        const val ProfileShare = "profile_share"
        const val ReplyShare = "reply_share"
    }
}