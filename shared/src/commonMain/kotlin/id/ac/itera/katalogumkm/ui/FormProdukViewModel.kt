package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.Sumber
import id.ac.itera.katalogumkm.data.pesanUntuk
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// ViewModel form produk yang mengolah input pengguna dan mengirim ke Sumber jaringan (HTTP POST)
class FormProdukViewModel(
    private val sumber: Sumber<Produk> // PERUBAHAN: Menerima interface Sumber<Produk>
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormProdukUiState())
    val uiState: StateFlow<FormProdukUiState> = _uiState.asStateFlow()

    fun ubahNama(teks: String) = _uiState.update { it.copy(nama = teks.take(40), pesan = null) }
    fun ubahHarga(teks: String) = _uiState.update { it.copy(harga = teks.filter { c -> c.isDigit() }.take(9), pesan = null) }
    fun ubahStok(teks: String) = _uiState.update { it.copy(stok = teks.filter { c -> c.isDigit() }.take(4), pesan = null) }
    fun pilihKategori(kategori: String) = _uiState.update { it.copy(kategori = kategori, pesan = null) }
    fun pilihUmkm(id: Int) = _uiState.update { it.copy(umkmId = id, pesan = null) }
    fun ubahDeskripsi(teks: String) = _uiState.update { it.copy(deskripsi = teks.take(100), pesan = null) }

    fun simpan() {
        val s = _uiState.value
        if (!s.bisaSimpan) return

        viewModelScope.launch {
            // Tampilkan indikator loading/sedang menyimpan pada tombol
            _uiState.value = s.copy(sedangMenyimpan = true, pesan = null)

            _uiState.value = try {
                // Mengirim produk ke REST API server (HTTP POST /produk)
                val tersimpan = sumber.kirim(s.keProduk())

                // Jika berhasil, atur tersimpan = true agar UI mengarahkan kembali ke Katalog
                FormProdukUiState(
                    pesan = "\"${tersimpan.nama}\" berhasil ditambahkan",
                    tersimpan = true
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Jika error (misal offline), simpan input form lama agar data pengguna TIDAK hilang
                s.copy(sedangMenyimpan = false, pesan = pesanUntuk(e))
            }
        }
    }
}

// Extension function mengonversi state form UI menjadi objek model Produk
fun FormProdukUiState.keProduk() = Produk(
    id = 0, // ID diisi 0 karena ID asli akan dibuat otomatis oleh database server
    nama = nama.trim(),
    harga = hargaAngka ?: 0L,
    kategori = kategori,
    umkmId = umkmId,
    deskripsi = deskripsi.trim(),
    fotoUrl = null,
    status = if ((stokAngka ?: 0) > 0) StatusProduk.Tersedia(stokAngka ?: 0) else StatusProduk.Habis
)