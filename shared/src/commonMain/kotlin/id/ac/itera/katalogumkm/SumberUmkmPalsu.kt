package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Umkm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SumberUmkmPalsu : Sumber<Umkm> {
    private val daftar = listOf(
        Umkm(1, "Warung Bu Sari", "Sari", "Depan gerbang utama kampus", "07.00-21.00", null),
        Umkm(2, "Kopi Kampus", "Dimas", "Samping asrama mahasiswa", "09.00-23.00", null),
        Umkm(3, "Fotocopy Berkah", "Rahmat", "Dekat gedung kuliah umum", "08.00-20.00", null),
    )

    override suspend fun ambil(id: Int): Umkm {
        delay(800)
        return daftar.first { it.id == id }
    }

    override fun pantau(id: Int): Flow<Umkm> = flow { emit(ambil(id)) }
}