package org.practice.project.dataBaseFactory

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.practice.org.AppDatabase
import org.practice.project.sqlDElight.data.local.DataBaseDriverFactory

class AndroidDatabaseDriverFactory(
    private val context: Context
): DataBaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            context,
            name = "database"
        )
    }
}