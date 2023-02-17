package com.ninegag.app.shared

import com.ninegag.app.shared.config.*
import com.ninegag.app.shared.config.CurrentPlatform
import com.ninegag.app.shared.config.PlatformEnum

lateinit var appConfig: BaseAppConfig
    private set
lateinit var appUserAgentConfig: UserAgentConfig
    private set

/**
 * Set configuration
 */
internal fun initializeConfig(env: Env, userAgentConfig: UserAgentConfig) {
    when (CurrentPlatform) {
        PlatformEnum.ANDROID -> {
            appConfig = when (env)  {
                Env.STAGING -> {
                    StgAndroidAppConfig
                }
                Env.RELEASE -> {
                    ReleaseAndroidAppConfig
                }
            }
        }
        PlatformEnum.IOS -> {
            appConfig = when (env)  {
                Env.STAGING -> {
                    StgIosAppConfig
                }
                Env.RELEASE -> {
                    ReleaseIosAppConfig
                }
            }
        }
    }
    appUserAgentConfig = userAgentConfig
}



