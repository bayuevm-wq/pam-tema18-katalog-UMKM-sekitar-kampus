package id.ac.itera.katalogumkm.nav

// Tiga tujuan utama di menu bawah.
// Simbol memakai emoji supaya tidak perlu menambah library ikon.
data class Tab(val label: String, val simbol: String, val rute: Any)

val TAB = listOf(
    Tab("Katalog", "🏪", RuteKatalog),
    Tab("Asisten", "🤖", RuteAsisten),
    Tab("Profil", "👤", RuteProfil)
)
