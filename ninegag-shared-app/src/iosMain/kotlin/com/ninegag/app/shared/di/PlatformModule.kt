package com.ninegag.app.shared.di

import com.ninegag.app.shared.AppContext
import com.ninegag.app.shared.IosAppContextImpl
import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.ninegag.app.shared.infra.local.DatabaseDriverFactory
import com.under9.shared.core.coroutines.PlatformDispatcherProvider
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSBundle

actual fun platformModule(): Module = module {
    single {
        SharedNineGagDatabase(DatabaseDriverFactory().createDriver())
    }
    single(named("DispatcherIO")) { Dispatchers.Default }
    single {
        PlatformDispatcherProvider()
    }
    single<AppContext<NSBundle>> {
        IosAppContextImpl(NSBundle.mainBundle)
    }
//    single { NSBundle.mainBundle }
}
