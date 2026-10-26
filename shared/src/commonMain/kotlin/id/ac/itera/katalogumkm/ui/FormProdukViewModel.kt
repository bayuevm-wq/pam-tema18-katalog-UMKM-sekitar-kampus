package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.PenyimpananProduk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormProdukViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FormProdukUiState())   // hanya ViewModel yang mengubah
    val uiState: StateFlow<FormProdukUiState> = _uiState.asStateFlow() // layar hanya membaca

    // Aturan input dijaga di sini, bukan di layar
    fun ubahNama(teks: String) =
        _uiState.update { it.copy(nama = teks.take(40), pesan = null) }

    fun ubahHarga(teks: String) =
        _uiState.update { it.copy(harga = teks.filter { c -> c.isDigit() }.take(9), pesan = null) }

    fun ubahStok(teks: String) =
        _uiState.update { it.copy(stok = teks.filter { c -> c.isDigit() }.take(4), pesan = null) }

    fun pilihKategori(kategori: String) =
        _uiState.update { it.copy(kategori = kategori, pesan = null) }

    fun pilihUmkm(id: Int) =
        _uiState.update { it.copy(umkmId = id, pesan = null) }

    fun ubahDeskripsi(teks: String) =
        _uiState.update { it.copy(deskripsi = teks.take(100), pesan = null) }

    fun simpan() {
        val s = _uiState.value
        if (!s.bisaSimpan) return                 // simpan hanya bila valid
        viewModelScope.launch {
            _uiState.update { it.copy(sedangMenyimpan = true) }
            delay(500)                            // pura-pura menunggu server
            PenyimpananProduk.tambah(
                nama = s.nama.trim(),
                harga = s.hargaAngka ?: 0L,
                stok = s.stokAngka ?: 0,
                kategori = s.kategori,
                umkmId = s.umkmId,
                deskripsi = s.deskripsi.trim()
            )
            _uiState.value = FormProdukUiState(pesan = "\"${s.nama.trim()}\" berhasil ditambahkan")
        }
    }
}
