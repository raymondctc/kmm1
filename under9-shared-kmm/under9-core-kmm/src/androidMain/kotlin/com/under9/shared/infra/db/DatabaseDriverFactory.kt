package com.under9.shared.infra.db

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver

import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val schema: SqlDriver.Schema,
    private val context: Context,
    private val name: String?
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(schema, context, name)
    }
}