package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Umkm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberUmkmPalsu : Sumber<Umkm> {
    private val daftar = listOf(
        Umkm(id = 1, nama = "Warung Bu Sari", pemilik = "Sari", lokasi = "Depan gerbang utama kampus", jamBuka = "07.00-21.00", noWhatsapp = null),
        Umkm(id = 2, nama = "Kopi Kampus", pemilik = "Dimas", lokasi = "Samping asrama mahasiswa", jamBuka = "09.00-23.00", noWhatsapp = null),
        Umkm(id = 3, nama = "Fotocopy Berkah", pemilik = "Rahmat", lokasi = "Dekat gedung kuliah umum", jamBuka = "08.00-20.00", noWhatsapp = null)
    )

    override suspend fun semua(): List<Umkm> = daftar

    override suspend fun kirim(baru: Umkm): Umkm = baru

    override suspend fun ambil(id: Int): Umkm {
        delay(800)
        return daftar.first { it.id == id }
    }

    override fun pantau(id: Int): Flow<Umkm> = flow { emit(ambil(id)) }
}