package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.db.DrafProduk
import id.ac.itera.katalogumkm.db.KatalogDb

// Membungkus query draf, supaya ViewModel tidak mengenal database secara langsung.
class PenyimpananDraf(private val db: KatalogDb) {

    private val q get() = db.drafQueries

    fun ambil(): DrafProduk? = q.ambilDraf().executeAsOneOrNull()

    fun simpan(
        nama: String,
        harga: String,
        stok: String,
        kategori: String,
        umkmId: Int,
        deskripsi: String
    ) = q.simpanDraf(nama, harga, stok, kategori, umkmId.toLong(), deskripsi)

    fun hapus() = q.hapusDraf()
}