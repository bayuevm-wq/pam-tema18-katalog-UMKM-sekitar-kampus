package id.ac.itera.katalogumkm.nav

// Tiga tujuan utama di menu bawah.
data class Tab(val label: String, val rute: Any)

val TAB = listOf(
    Tab("Katalog", RuteKatalog),
    Tab("Asisten", RuteAsisten),
    Tab("Profil", RuteProfil)
)
