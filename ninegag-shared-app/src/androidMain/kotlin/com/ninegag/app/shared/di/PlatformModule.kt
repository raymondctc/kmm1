package com.ninegag.app.shared.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.ninegag.app.shared.AndroidAppContextImpl
import com.ninegag.app.shared.AppContext
import com.ninegag.app.shared.infra.local.DatabaseDriverFactory
import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.Settings
import com.under9.shared.core.coroutines.PlatformDispatcherProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        SharedNineGagDatabase(DatabaseDriverFactory(get()).createDriver())
    }

    single<Settings> {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(get())
        SharedPreferencesSettings(sharedPrefs)
    }

    single {
        PlatformDispatcherProvider()
    }

    single<AppContext<Context>> {
        AndroidAppContextImpl(get())
    }
}