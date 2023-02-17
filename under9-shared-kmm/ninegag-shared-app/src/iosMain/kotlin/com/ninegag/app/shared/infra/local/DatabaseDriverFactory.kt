package com.ninegag.app.shared.infra.local

import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(SharedNineGagDatabase.Schema, "shared-ninegag.db")
    }
}