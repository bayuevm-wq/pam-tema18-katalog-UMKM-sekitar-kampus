package id.ac.itera.katalogumkm.data

import app.cash.sqldelight.db.SqlDriver

// Janji di commonMain: setiap platform punya DriverFactory dengan createDriver().
// Cara membuka SQLite berbeda per platform, jadi isinya ditulis di androidMain, iosMain, jvmMain.
expect class DriverFactory {
    fun createDriver(): SqlDriver
}
