package com.ninegag.app.shared.di

import com.ninegag.app.shared.config.Env
import com.ninegag.app.shared.config.UserAgentConfig
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.KoinApplication
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

fun initSdk(
    userDefaults: NSUserDefaults,
    userAgentConfig: UserAgentConfig,
    doOnStartup: () -> Unit,
    env: Env
): KoinApplication {
    return initKoin(
        module {
            platformModule()
            single<Settings> { NSUserDefaultsSettings(userDefaults) }
            single {
                doOnStartup
            }
        }, userAgentConfig, env
    )
}