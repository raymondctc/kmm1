package com.ninegag.app.shared.di

import com.ninegag.app.shared.di.module.*
import com.ninegag.app.shared.appConfig
import com.ninegag.app.shared.appUserAgentConfig
import com.ninegag.app.shared.config.*
import com.ninegag.app.shared.di.module.localDatasourceModule
import com.ninegag.app.shared.di.module.remoteDatasourceModule
import com.ninegag.app.shared.di.module.repositoryModule
import com.ninegag.app.shared.di.module.useCaseModule
import com.ninegag.app.shared.domain.GagHttpHeaderValueManager
import com.ninegag.app.shared.infra.remote.GagHttpEngineFactory
import com.ninegag.app.shared.infra.remote.tag.model.ApiNavListDataSerializer
import com.ninegag.app.shared.infra.remote.tag.model.ApiNavListHomeDataSerializer
import com.ninegag.app.shared.infra.remote.tag.model.ApiNavListResponse
import com.ninegag.app.shared.infra.remote.tag.model.ApiNavListSerializer
import com.ninegag.app.shared.initializeConfig
import com.under9.shared.core.coroutines.AppMainScope
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


fun initKoin(
    appModule: Module,
    userAgentConfig: UserAgentConfig,
    env: Env
): KoinApplication {
    initializeConfig(env, userAgentConfig)
    if (appConfig.debug) {
        Napier.base(DebugAntilog())
    }
    Napier.i("Logging enabled")

    val koinApplication = startKoin {
        modules(
            appModule,
            featureModules,
            networkModule,
            repositoryModule,
            remoteDatasourceModule,
            localDatasourceModule,
            useCaseModule,
            facadeModule,
            // viewmodel
            // mapping
            platformModule(),
            sharedUtilModule,
        )
    }

    val koin = koinApplication.koin
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

    return koinApplication
}

internal val sharedUtilModule = module {
    factory { GagHttpHeaderValueManager(get(), appUserAgentConfig) }
    single<CoroutineScope> { AppMainScope(get()) }
}

private val networkModule = module {
    single {
        val httpHeaderValueManager = GagHttpHeaderValueManager(get(), appUserAgentConfig).apply {
            updateRequestHeaderValue(
                appId = appConfig.appId,
                packageName = appUserAgentConfig.packageName,
                packageVersion = appUserAgentConfig.packageVersion,
                deviceType = if (CurrentPlatform == PlatformEnum.IOS) "ios" else "android"
            )
        }
        val factory = GagHttpEngineFactory(get(), appUserAgentConfig, get())
        HttpClient(factory.createHttpEngine()) {
            install(ContentNegotiation) {
                val json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    explicitNulls = false
                    isLenient = true
                    serializersModule = SerializersModule {
                        contextual(ApiNavListResponse::class, ApiNavListSerializer(
                            apiNavListDataSerializer = ApiNavListDataSerializer(
                                ApiNavListHomeDataSerializer
                            )
                        ))
                    }
                }
                json(json = json)
            }
//            install(Auth) {
//                customAppAuth {
//                    loadTokens { tokenValueManager.loadTokens() }
//                    refreshTokens { tokenValueManager.loadTokens() }
//                }
//            }
            install(DefaultRequest)
            install(UserAgent) {
                agent = httpHeaderValueManager.createRequestHeader().userAgent
            }
            install(HttpCache)
            engine {
                factory.setEngineConfig(this)
            }
            defaultRequest {
                url("${appConfig.appApiEndpoint}/v2/")
            }
        }
    }
}

// cp ninegag-shared-lib/build/ ../../9gag-ios/under9-shared-kmm-export-ios/