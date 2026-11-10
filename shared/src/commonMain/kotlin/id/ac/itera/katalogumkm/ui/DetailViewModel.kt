package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.Sumber
import id.ac.itera.katalogumkm.data.contohUmkm
import id.ac.itera.katalogumkm.data.pesanUntuk
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.Umkm
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sealed class untuk merepresentasikan 4 kondisi UI Layar Detail
sealed class DetailUiState {
    data object Memuat : DetailUiState()
    data class Ada(val produk: Produk, val umkm: Umkm?) : DetailUiState()
    data object TidakDitemukan : DetailUiState()
    data class Gagal(val pesan: String) : DetailUiState() // BARU: Menampung pesan error jaringan
}

// ViewModel yang mengambil 1 produk dari Sumber (server) secara asinkron
class DetailViewModel(
    private val id: Int,
    private val sumber: Sumber<Produk> // BARU: Menggunakan interface Sumber<Produk>
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Memuat)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        muat()
    }

    fun muat() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Memuat
            _uiState.value = try {
                // Mengambil produk tunggal dari server berdasarkan ID
                val produk = sumber.ambil(id)
                DetailUiState.Ada(produk, contohUmkm.find { it.id == produk.umkmId })
            } catch (e: CancellationException) {
                throw e // Agar coroutine lifecycle Compose tidak terganggu
            } catch (e: ClientRequestException) {
                // HTTP status 404 berarti data memang tidak ada di database server
                if (e.response.status.value == 404) DetailUiState.TidakDitemukan
                else DetailUiState.Gagal(pesanUntuk(e))
            } catch (e: NoSuchElementException) {
                DetailUiState.TidakDitemukan
            } catch (e: Exception) {
                // Menangkap error koneksi (offline/server down)
                DetailUiState.Gagal(pesanUntuk(e))
            }
        }
    }
}