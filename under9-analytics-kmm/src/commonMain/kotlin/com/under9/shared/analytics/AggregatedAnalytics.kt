package com.under9.shared.analytics

import com.under9.shared.analytics.model.Event
import com.under9.shared.analytics.model.UserProperty

class AggregatedAnalytics: Analytics {
    private val analyticsImpls = mutableListOf<Analytics>()

    override fun trackEvent(event: Event) {
        analyticsImpls.forEach { it.trackEvent(event) }
    }

    override fun updateUserProperty(userProperty: UserProperty) {
        analyticsImpls.forEach { it.updateUserProperty(userProperty) }
    }

    override fun updateUserProperty(userPropertyMap: Map<String, Any>) {
        analyticsImpls.forEach { it.updateUserProperty(userPropertyMap) }
    }

    fun addAnalytics(analytics: Analytics): AggregatedAnalytics {
        analyticsImpls.add(analytics)
        return this
    }

    fun removeAnalytics(analytics: Analytics) = analyticsImpls.remove(analytics)

    fun size(): Int {
        return analyticsImpls.size
    }
}