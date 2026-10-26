package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object PenyimpananProduk {

    private val _daftar = MutableStateFlow(contohProduk)

    val daftar: StateFlow<List<Produk>> = _daftar.asStateFlow()

    fun tambah(
        nama: String,
        harga: Long,
        stok: Int,
        kategori: String,
        umkmId: Int,
        deskripsi: String
    ) = _daftar.update { lama ->

        val idBaru = (lama.maxOfOrNull { it.id } ?: 0) + 1

        val status = if (stok > 0) {
            StatusProduk.Tersedia(stok)
        } else {
            StatusProduk.Habis
        }

        lama + Produk(
            idBaru,
            nama,
            harga,
            kategori,
            umkmId,
            deskripsi,
            null,
            status
        )
    }
}