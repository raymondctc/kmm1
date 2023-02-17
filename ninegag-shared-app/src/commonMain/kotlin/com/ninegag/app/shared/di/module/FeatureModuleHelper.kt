package com.ninegag.app.shared.di.module

import com.ninegag.app.shared.ui.tag.NavTagListUiHandler
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FeatureModuleHelper: KoinComponent {
    val navTagListHandler: NavTagListUiHandler by inject()
}