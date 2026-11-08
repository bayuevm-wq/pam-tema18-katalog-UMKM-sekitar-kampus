package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Produk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberProdukJaringan(private val api: ProdukApi) : Sumber<Produk> {

    private val tambahanLokal = mutableListOf<Produk>()
    private var idTerbesar = 0

    override suspend fun semua(): List<Produk> {
        val dariServer = api.semua().map { it.keProduk() }
        val idServer = dariServer.map { it.id }.toSet()
        idTerbesar = maxOf(idTerbesar, dariServer.maxOfOrNull { it.id } ?: 0)
        return dariServer + tambahanLokal.filter { it.id !in idServer }
    }

    override suspend fun ambil(id: Int): Produk =
        tambahanLokal.find { it.id == id } ?: api.ambil(id).keProduk()

    override fun pantau(id: Int): Flow<Produk> = flow { emit(ambil(id)) }

    override suspend fun kirim(baru: Produk): Produk {
        val dariServer = api.kirim(baru.keBaruDto()).keProduk()
        val sudahDipakai = dariServer.id <= idTerbesar || tambahanLokal.any { it.id == dariServer.id }
        val tersimpan = if (sudahDipakai) dariServer.copy(id = idTerbesar + 1) else dariServer
        idTerbesar = maxOf(idTerbesar, tersimpan.id)
        tambahanLokal += tersimpan
        return tersimpan
    }
}
