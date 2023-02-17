package com.under9.shared.util

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object KmmCoreGlobal {
    // TODO use app build config
    val IS_DEBUG = false
}