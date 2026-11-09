package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.Sumber
import id.ac.itera.katalogumkm.data.pesanUntuk
import id.ac.itera.katalogumkm.model.Produk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KatalogViewModel(private val sumber: Sumber<Produk>) : ViewModel() {

    private val _uiState = MutableStateFlow<KatalogUiState>(KatalogUiState.Memuat)
    val uiState: StateFlow<KatalogUiState> = _uiState.asStateFlow()

    init { muat() }

    fun muat() {
        viewModelScope.launch {
            _uiState.value = KatalogUiState.Memuat
            _uiState.value = try {
                KatalogUiState.Berhasil(sumber.semua())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                KatalogUiState.Gagal(pesanUntuk(e))
            }
        }
    }
}