
package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.PenyimpananProduk
import id.ac.itera.katalogumkm.data.contohUmkm
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.Umkm
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed class DetailUiState {
    data object Memuat : DetailUiState()
    data class Ada(val produk: Produk, val umkm: Umkm?) : DetailUiState()
    data object TidakDitemukan : DetailUiState()
}

class DetailViewModel(id: Int) : ViewModel() {

    val uiState: StateFlow<DetailUiState> =
        PenyimpananProduk.daftar
            .map { semua ->
                val produk = semua.find { it.id == id }

                if (produk == null) {
                    DetailUiState.TidakDitemukan
                } else {
                    DetailUiState.Ada(
                        produk,
                        contohUmkm.find { it.id == produk.umkmId }
                    )
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                DetailUiState.Memuat
            )
}
