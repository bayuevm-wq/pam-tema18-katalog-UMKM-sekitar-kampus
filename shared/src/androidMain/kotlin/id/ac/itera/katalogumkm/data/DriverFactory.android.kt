package id.ac.itera.katalogumkm.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import id.ac.itera.katalogumkm.db.KatalogDb

// Android butuh Context, jadi constructor-nya berbeda. Itu boleh.
actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(KatalogDb.Schema, context, "katalogumkm.db")
}
