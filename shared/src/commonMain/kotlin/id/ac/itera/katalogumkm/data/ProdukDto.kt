package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import kotlinx.serialization.Serializable

// Bentuk data PERSIS seperti JSON dari server. Nama properti = nama kunci JSON.
@Serializable
data class ProdukDto(
    val id: Int,
    val nama: String,
    val harga: Long,
    val kategori: String,
    val umkmId: Int,
    val deskripsi: String = "",        // nilai bawaan = boleh tidak dikirim server
    val fotoUrl: String? = null,
    val status: String,                // "Tersedia", "PreOrder", atau "Habis"
    val stok: Int = 0,
    val estimasiHari: Int = 0
)

// Yang dikirim saat POST. Tanpa id, karena id dibuat server.
@Serializable
data class ProdukBaruDto(
    val nama: String,
    val harga: Long,
    val kategori: String,
    val umkmId: Int,
    val deskripsi: String,
    val fotoUrl: String? = null,
    val status: String,
    val stok: Int,
    val estimasiHari: Int
)

// Penerjemah DTO (teks dari server) -> Produk milik aplikasi (sealed class)
fun ProdukDto.keProduk() = Produk(
    id = id,
    nama = nama,
    harga = harga,
    kategori = kategori,
    umkmId = umkmId,
    deskripsi = deskripsi,
    fotoUrl = fotoUrl,
    status = when (status) {
        "Tersedia" -> StatusProduk.Tersedia(stok)
        "PreOrder" -> StatusProduk.PreOrder(estimasiHari)
        else -> StatusProduk.Habis          // status yang tidak dikenal tidak membuat crash
    }
)

// Penerjemah arah sebaliknya: Produk -> DTO untuk dikirim
fun Produk.keBaruDto(): ProdukBaruDto {
    val (teks, stok, estimasi) = when (val s = status) {
        is StatusProduk.Tersedia -> Triple("Tersedia", s.stok, 0)
        is StatusProduk.PreOrder -> Triple("PreOrder", 0, s.estimasiHari)
        StatusProduk.Habis -> Triple("Habis", 0, 0)
    }
    return ProdukBaruDto(nama, harga, kategori, umkmId, deskripsi, fotoUrl, teks, stok, estimasi)
}
