package com.under9.shared.infra.db

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory(
    private val schema: SqlDriver.Schema,
    private val name: String
) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema, name)
    }
}