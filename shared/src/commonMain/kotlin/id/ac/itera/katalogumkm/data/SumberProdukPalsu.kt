package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberProdukPalsu : Sumber<Produk> {

    private val daftar = listOf(
        Produk(1, "Keripik Pisang Coklat", 15000, "Makanan", 1,
            "Keripik pisang renyah dengan lumuran coklat", null, StatusProduk.Tersedia(10)),
        Produk(2, "Es Kopi Susu Gula Aren", 12000, "Minuman", 2,
            "Kopi susu dingin, gula aren asli", null, StatusProduk.Tersedia(25)),
        Produk(3, "Jasa Print dan Jilid", 5000, "Jasa", 3,
            "Print A4 dan jilid lakban", null, StatusProduk.PreOrder(1)),
    )

    override suspend fun ambil(id: Int): Produk {
        delay(1000) // pura-pura menunggu server
        return daftar.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Produk #$id tidak ditemukan")
    }

    override fun pantau(id: Int): Flow<Produk> = flow {
        val awal = ambil(id)
        emit(awal)
        delay(1500)
        emit(awal.copy(status = StatusProduk.Tersedia(3)))
        delay(1500)
        emit(awal.copy(status = StatusProduk.Habis))
        delay(2000)
        emit(awal.copy(status = StatusProduk.PreOrder(2)))
    }
}