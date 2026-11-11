
package id.ac.itera.katalogumkm.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import id.ac.itera.katalogumkm.db.KatalogDb
import id.ac.itera.katalogumkm.db.ProdukEntity
import id.ac.itera.katalogumkm.model.Produk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SumberProdukOffline(
    private val db: KatalogDb,
    private val api: ProdukApi
) : Sumber<Produk> {

    private val q get() = db.produkQueries

    // Mengambil daftar produk dari database secara otomatis
    fun pantauSemua(): Flow<List<Produk>> =
        q.semua()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { daftar -> daftar.map { it.keProduk() } }

    // Mengambil data dari server lalu menyimpannya ke database
    suspend fun segarkan() {
        val dariServer = api.semua()

        withContext(Dispatchers.Default) {
            db.transaction {
                q.hapusSemua()
                dariServer.forEach { simpanDto(it) }
            }
        }
    }

    // Mengambil semua produk dari database
    override suspend fun semua(): List<Produk> =
        withContext(Dispatchers.Default) {
            q.semua().executeAsList().map { it.keProduk() }
        }

    // Mengambil detail produk dari database terlebih dahulu
    override suspend fun ambil(id: Int): Produk {
        val lokal = withContext(Dispatchers.Default) {
            q.byId(id.toLong()).executeAsOneOrNull()
        }

        if (lokal != null) return lokal.keProduk()

        val dto = api.ambil(id)

        withContext(Dispatchers.Default) {
            simpanDto(dto)
        }

        return dto.keProduk()
    }

    // Memantau perubahan detail produk
    override fun pantau(id: Int): Flow<Produk> =
        q.byId(id.toLong())
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .filterNotNull()
            .map { it.keProduk() }

    // Mengirim produk baru dan menyimpannya ke database
    override suspend fun kirim(baru: Produk): Produk {
        val dto = api.kirim(baru.keBaruDto())

        withContext(Dispatchers.Default) {
            simpanDto(dto)
        }

        return dto.keProduk()
    }

    // Menyimpan data DTO ke database
    private fun simpanDto(d: ProdukDto) = q.simpan(
        id = d.id.toLong(),
        nama = d.nama,
        harga = d.harga,
        kategori = d.kategori,
        umkmId = d.umkmId.toLong(),
        deskripsi = d.deskripsi,
        fotoUrl = d.fotoUrl,
        status = d.status,
        stok = d.stok.toLong(),
        estimasiHari = d.estimasiHari.toLong()
    )
}

// Mengubah data database menjadi model Produk
fun ProdukEntity.keProduk(): Produk = ProdukDto(
    id = id.toInt(),
    nama = nama,
    harga = harga,
    kategori = kategori,
    umkmId = umkmId.toInt(),
    deskripsi = deskripsi,
    fotoUrl = fotoUrl,
    status = status,
    stok = stok.toInt(),
    estimasiHari = estimasiHari.toInt()
).keProduk()
