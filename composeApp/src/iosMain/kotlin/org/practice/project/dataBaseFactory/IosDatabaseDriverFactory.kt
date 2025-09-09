package org.practice.project.dataBaseFactory



import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.practice.org.AppDatabase
import org.practice.project.sqlDElight.data.local.DataBaseDriverFactory




class IosDatabaseDriverFactory(): DataBaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            AppDatabase.Schema,
            name = "database"
        )
    }
}