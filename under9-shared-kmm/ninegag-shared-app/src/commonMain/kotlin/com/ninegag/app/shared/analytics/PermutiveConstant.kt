package com.ninegag.app.shared.analytics

import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

object PermutiveConstant {

    object Param {
        const val Title = "title"
        const val Url = "url"
        const val Referrer = "referrer"
        const val EventProperties = "eventProperties"
        const val ScreenInfo = "screenInfo"
        const val CustomEvent = "customEvent"
    }

    object User {
        const val Key = "user"
        const val UserId = "id"
        const val Gender = "gender"
        const val Age = "age"
        const val SocialConnect = "social_connect"
        const val SensitiveControl = "sensitive_content_on"
        const val TagFavorited = "tags_favorited"

        object GenderType {
            val Values = _Values
            object _Values {
                const val Male = "M"
                const val Female = "F"
                const val Unspecified = "X"
                val Empty = null
            }
        }

        object MemberType {
            const val Key = "member_type"
            val Values = _Values
            object _Values {
                const val Free = "Free"
                const val Pro = "Pro"
                const val ProPlus = "Pro+"
                const val Guest = "Guest"
            }
        }
    }

    object Post {
        const val Key = "post"
        const val Id = "id"
        const val Title = "title"
        const val Tags = "tags"
        const val Sensitive = "sensitive"
        const val AnnotationTags = "annotation_tags"

        object EngagementType {
            const val Key = "engagement_type"
            @JvmField
            val Values = _Values

            object _Values {
                const val Saved = "Saved"
                const val Shared = "Shared"
                const val Commented = "Commented"
                const val Voted = "Voted"
                const val Downloaded = "Downloaded"
            }
        }

        const val PostEngagementCustomEvent = "PostEngagement"
    }


}