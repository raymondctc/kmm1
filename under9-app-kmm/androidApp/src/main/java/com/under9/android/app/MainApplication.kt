package com.under9.android.app

import android.app.Application
import android.content.Context
import com.ninegag.app.shared.config.Env
import com.ninegag.app.shared.config.UserAgentConfig
import com.ninegag.app.shared.di.initKoin
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val userAgentConfig = UserAgentConfig(
            "com.ninegag.android.app.jokes",
            "61230000",
            { "v123-123-123" },
            { "9GAG Android SampleApp" }
        )
        initKoin(
            module {
                single<Context> { this@MainApplication }
                single(named("DispatcherIO")) { Dispatchers.IO }
                single {
                    // do on startup
                    {
                        Timber.d("Started koin")
                    }
                }
            },
            userAgentConfig,
            Env.STAGING,
        )
    }
}