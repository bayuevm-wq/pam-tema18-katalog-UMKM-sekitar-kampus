package id.ac.itera.katalogumkm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.ac.itera.katalogumkm.data.PenyimpananDraf
import id.ac.itera.katalogumkm.data.Sumber
import id.ac.itera.katalogumkm.data.pesanUntuk
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)                 // debounce masih berlabel pratinjau[cite: 1]
class FormProdukViewModel(
    private val sumber: Sumber<Produk>,
    private val draf: PenyimpananDraf      // BARU (Checkpoint 7): draf form di database[cite: 1]
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormProdukUiState())
    val uiState: StateFlow<FormProdukUiState> = _uiState.asStateFlow()

    init {
        // 1. Saat form dibuka: masukkan draf lama (kalau ada) ke form[cite: 1]
        draf.ambil()?.let { d ->
            _uiState.update {
                it.copy(
                    nama = d.nama,
                    harga = d.harga,
                    stok = d.stok,
                    kategori = d.kategori,
                    umkmId = d.umkmId.toInt(),
                    deskripsi = d.deskripsi
                )
            }
        }

        // 2. Setiap perubahan isi form disimpan otomatis,
        //    setelah pengguna berhenti mengetik setengah detik[cite: 1]
        viewModelScope.launch {
            _uiState.debounce(500).collect { s ->
                if (!s.tersimpan) {
                    draf.simpan(s.nama, s.harga, s.stok, s.kategori, s.umkmId, s.deskripsi)
                }
            }
        }
    }

    // Fungsi pengubah field form (TIDAK berubah dari CP6)[cite: 1]
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
            _uiState.value = s.copy(sedangMenyimpan = true, pesan = null)
            _uiState.value = try {
                val tersimpan = sumber.kirim(s.keProduk())
                draf.hapus()       // BARU: produk sudah di server, draf tidak diperlukan lagi[cite: 1]
                FormProdukUiState(
                    pesan = "\"${tersimpan.nama}\" berhasil ditambahkan",
                    tersimpan = true
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                s.copy(sedangMenyimpan = false, pesan = pesanUntuk(e))
            }
        }
    }
}

fun FormProdukUiState.keProduk() = Produk(
    id = 0,
    nama = nama.trim(),
    harga = hargaAngka ?: 0L,
    kategori = kategori,
    umkmId = umkmId,
    deskripsi = deskripsi.trim(),
    fotoUrl = null,
    status = if ((stokAngka ?: 0) > 0) StatusProduk.Tersedia(stokAngka ?: 0) else StatusProduk.Habis
)