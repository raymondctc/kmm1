package com.ninegag.app.shared.util

/**
 * A mapper for mapping items to UI model
 */
interface Mapper<FROM, TO> {
    fun mapTo(from: FROM): TO
}