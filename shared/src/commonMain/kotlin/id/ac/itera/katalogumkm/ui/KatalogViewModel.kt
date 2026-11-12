package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.SumberProdukOffline
import id.ac.itera.katalogumkm.model.Produk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class KatalogViewModel(private val sumber: SumberProdukOffline) : ViewModel() {

    // Daftar SELALU dari database. Berubah sendiri setiap kali tabel berubah.
    val produk: StateFlow<List<Produk>> = sumber.pantauSemua()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Hasil segarkan: null kalau berhasil, berisi teks kalau offline
    private val _pesan = MutableStateFlow<String?>(null)
    val pesan: StateFlow<String?> = _pesan.asStateFlow()

    // true selama sedang mengambil dari server (untuk spinner di pemakaian pertama)
    private val _menyegarkan = MutableStateFlow(false)
    val menyegarkan: StateFlow<Boolean> = _menyegarkan.asStateFlow()

    init { segarkan() }

    // Dipanggil saat dibuka dan oleh tombol "Coba lagi"
    fun segarkan() {
        viewModelScope.launch {
            _menyegarkan.value = true
            try {
                sumber.segarkan()
                _pesan.value = null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _pesan.value = "Offline. Menampilkan data terakhir."
            } finally {
                _menyegarkan.value = false
            }
        }
    }
}