package com.ninegag.app.shared.di.module.tag

import com.ninegag.app.shared.ui.tag.NavTagListUiHandler
import org.koin.dsl.module

internal val navTagListModule = module {
    factory { NavTagListUiHandler(get(), get(), get()) }
}