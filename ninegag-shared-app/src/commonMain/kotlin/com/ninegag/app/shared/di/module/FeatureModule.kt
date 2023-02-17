package com.ninegag.app.shared.di.module

import com.ninegag.app.shared.di.module.tag.navTagListModule
import org.koin.dsl.module

internal val featureModules = module {
    includes(
        navTagListModule
    )
}