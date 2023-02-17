package com.ninegag.app.shared.infra.local

import android.content.Context
import com.ninegag.app.shared.db.SharedNineGagDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SharedNineGagDatabase.Schema, context, "shared-ninegag.db")
    }
}