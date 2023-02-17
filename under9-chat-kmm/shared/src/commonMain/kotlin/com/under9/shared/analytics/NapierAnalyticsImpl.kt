package com.under9.shared.analytics

import com.under9.shared.analytics.model.Event
import com.under9.shared.analytics.model.UserProperty
import io.github.aakira.napier.Napier

/**
 * A dummy analytics tracker to demonstrate [AggregatedAnalytics] is working
 */
class NapierAnalyticsImpl: Analytics {
    override fun trackEvent(event: Event) {
        Napier.i("---TrackEvent=$event")
    }

    override fun updateUserProperty(userProperty: UserProperty) {
        Napier.i("---UpdateUserProperty=$userProperty")
    }

    override fun updateUserProperty(userPropertyMap: Map<String, Any>) {
        Napier.i("---UpdateUserPropertyMap=$userPropertyMap")
    }
}