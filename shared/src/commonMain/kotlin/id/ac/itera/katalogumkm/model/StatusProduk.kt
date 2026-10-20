package id.ac.itera.katalogumkm.model

sealed class StatusProduk {
    data class Tersedia(val stok: Int) : StatusProduk()
    data class PreOrder(val estimasiHari: Int) : StatusProduk()
    data object Habis : StatusProduk()
}
