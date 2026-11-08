package id.ac.itera.katalogumkm.data

import kotlinx.coroutines.flow.Flow


interface Sumber<T> {
    suspend fun semua(): List<T>
    suspend fun ambil(id: Int): T
    fun pantau(id: Int): Flow<T>
    suspend fun kirim(baru: T): T
}
