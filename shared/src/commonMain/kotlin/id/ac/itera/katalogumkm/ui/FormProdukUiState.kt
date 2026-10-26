package id.ac.itera.katalogumkm.ui

// Semua yang dibutuhkan layar form, dalam satu data class.
data class FormProdukUiState(
    val nama: String = "",
    val harga: String = "",           // String karena berasal dari TextField
    val stok: String = "",
    val kategori: String = "Makanan",
    val umkmId: Int = 1,
    val deskripsi: String = "",
    val sedangMenyimpan: Boolean = false,
    val pesan: String? = null         // hanya ada setelah produk tersimpan
) {
    // Nilai turunan: dihitung, tidak disimpan (state seminimal mungkin)
    val hargaAngka: Long? get() = harga.toLongOrNull()
    val stokAngka: Int? get() = stok.toIntOrNull()

    val namaValid: Boolean get() = nama.trim().length >= 3
    val hargaValid: Boolean get() = (hargaAngka ?: 0L) > 0L
    val stokValid: Boolean get() = stokAngka != null

    val bisaSimpan: Boolean
        get() = namaValid && hargaValid && stokValid && !sedangMenyimpan
}
