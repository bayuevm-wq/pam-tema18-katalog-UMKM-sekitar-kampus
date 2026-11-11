package id.ac.itera.katalogumkm.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import id.ac.itera.katalogumkm.db.KatalogDb

actual class DriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(KatalogDb.Schema, "katalogumkm.db")
}
