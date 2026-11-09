package id.ac.itera.katalogumkm.ui

import id.ac.itera.katalogumkm.model.Produk

sealed interface KatalogUiState {
    data object Memuat : KatalogUiState
    data class Berhasil(val produk: List<Produk>) : KatalogUiState
    data class Gagal(val pesan: String) : KatalogUiState
}