package com.under9.shared.analytics

import com.under9.shared.analytics.model.Event
import com.under9.shared.analytics.model.ScreenInfo
import com.under9.shared.analytics.model.UserProperty

interface Analytics {
    fun trackEvent(event: Event)

    fun updateUserProperty(userProperty: UserProperty)

    fun updateUserProperty(userPropertyMap: Map<String, Any>)
}