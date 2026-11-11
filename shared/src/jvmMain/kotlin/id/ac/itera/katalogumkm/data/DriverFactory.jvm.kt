package id.ac.itera.katalogumkm.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import id.ac.itera.katalogumkm.db.KatalogDb
import java.io.File
import java.util.Properties

// Desktop: database berupa BERKAS (bukan memori), supaya data bertahan setelah aplikasi ditutup.
// Schema diberikan ke driver, jadi tabel dibuat otomatis saat berkas belum ada.
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val berkas = File(System.getProperty("user.home"), "katalogumkm.db")
        return JdbcSqliteDriver("jdbc:sqlite:${berkas.absolutePath}", Properties(), KatalogDb.Schema)
    }
}
