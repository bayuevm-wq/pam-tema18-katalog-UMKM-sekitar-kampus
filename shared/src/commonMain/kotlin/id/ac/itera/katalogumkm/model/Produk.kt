package id.ac.itera.katalogumkm.model

data class Produk(
    val id: Int,
    val nama: String,
    val harga: Long,
    val kategori: String,
    val umkmId: Int,          // menghubungkan ke Umkm
    val deskripsi: String,
    val fotoUrl: String?,     // boleh kosong: belum semua produk punya foto
    val status: StatusProduk
)
