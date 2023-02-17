package com.under9.shared.chat.data

import com.under9.shared.analytics.AggregatedAnalytics
import com.under9.shared.analytics.Analytics
import com.under9.shared.analytics.NapierAnalyticsImpl
import com.under9.shared.chat.api.ApiServiceManager
import com.under9.shared.chat.db.ChatDatabase
import com.under9.shared.chat.util.ChatTokenValueManager
import com.under9.shared.core.coroutines.PlatformDispatcherProvider
import com.under9.shared.infra.db.DatabaseDriverFactory
import com.under9.shared.infra.db.Preferences
import com.under9.shared.infra.network.*
import com.under9.shared.util.KmmCoreGlobal
import kotlinx.coroutines.*
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object ObjectManager {

    lateinit var chatDatabase: ChatDatabase
        private set
    lateinit var platformDispatcherProvider: PlatformDispatcherProvider
        private set
    lateinit var backgroundDispatcher: CoroutineDispatcher
        private set
    lateinit var platformHttpFactory: PlatformHttpFactory
        private set
    lateinit var tokenValueManager: ChatTokenValueManager
        private set
    lateinit var headerValueManager: HttpHeaderValueManager
        private set
    lateinit var preferences: Preferences
        private set
    lateinit var analytics: Analytics
        private set
    lateinit var mixpanelAnalytics: Analytics
        private set

    fun init(
        platformDispatcherProvider: PlatformDispatcherProvider,
        platformHttpFactory: PlatformHttpFactory,
        databaseDriverFactory: DatabaseDriverFactory,
        tokenValueManager: ChatTokenValueManager,
        httpHeaderValueManager: HttpHeaderValueManager,
        preferences: Preferences,
        analytics: Analytics,
        mixpanelAnalytics: Analytics,
        isUsingStaging: Boolean
    ) {
        // Debug only
        if (KmmCoreGlobal.IS_DEBUG) {
            if (analytics is AggregatedAnalytics) {
                analytics.addAnalytics(NapierAnalyticsImpl())
            }
        }
        chatDatabase = ChatDatabase(databaseDriverFactory.createDriver())
        this.analytics = analytics
        this.mixpanelAnalytics = mixpanelAnalytics
        this.headerValueManager = httpHeaderValueManager
        this.tokenValueManager = tokenValueManager
        this.platformDispatcherProvider = platformDispatcherProvider
        this.backgroundDispatcher = platformDispatcherProvider.getIoDispatcher()
        this.platformHttpFactory = platformHttpFactory
        this.preferences = preferences

        // TODO migrate to app build config
        ApiServiceManager.isUsingStaging = isUsingStaging

        ApiServiceManager.client = platformHttpFactory.createClient(tokenValueManager, httpHeaderValueManager)
    }

    // Token value will be changed by different 9GAG users
    // Call to apply the latest value to HttpClient
    fun recreateServices(tokenValueManager: ChatTokenValueManager, headerValueManager: HttpHeaderValueManager) {
        this.tokenValueManager = tokenValueManager
        this.headerValueManager = headerValueManager
        ApiServiceManager.client = platformHttpFactory.createClient(tokenValueManager, headerValueManager)
    }

}