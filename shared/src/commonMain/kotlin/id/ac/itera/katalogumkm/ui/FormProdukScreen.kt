package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ac.itera.katalogumkm.model.Umkm

private val pilihanKategori = listOf("Makanan", "Minuman", "Jasa")

@Composable
fun FormProdukScreen(
    umkm: List<Umkm>,
    onKembali: () -> Unit,
    vm: FormProdukViewModel = viewModel { FormProdukViewModel() }
) {
    val state by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextButton(onClick = onKembali) { Text("< Kembali ke katalog") }
        Text("Tambah Produk", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        KolomTeks(
            label = "Nama produk",
            value = state.nama,
            onValueChange = vm::ubahNama,
            pesanError = if (state.nama.isNotEmpty() && !state.namaValid) "Minimal 3 huruf" else null
        )
        KolomTeks(
            label = "Harga (Rp)",
            value = state.harga,
            onValueChange = vm::ubahHarga,
            angka = true,
            pesanError = if (state.harga.isNotEmpty() && !state.hargaValid) "Harga harus lebih dari 0" else null
        )
        KolomTeks(label = "Stok", value = state.stok, onValueChange = vm::ubahStok, angka = true)
        KolomTeks(label = "Deskripsi (opsional)", value = state.deskripsi, onValueChange = vm::ubahDeskripsi)

        Text("Kategori", fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            pilihanKategori.forEach { kategori ->
                RadioButton(
                    selected = state.kategori == kategori,
                    onClick = { vm.pilihKategori(kategori) }
                )
                Text(kategori, modifier = Modifier.padding(end = 8.dp))
            }
        }

        Text("UMKM penjual", fontWeight = FontWeight.Bold)
        umkm.forEach { u ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.umkmId == u.id,
                    onClick = { vm.pilihUmkm(u.id) }
                )
                Text(u.nama)
            }
        }

        Button(
            onClick = vm::simpan,
            enabled = state.bisaSimpan,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.sedangMenyimpan) "Menyimpan..." else "Simpan")
        }

        state.pesan?.let { Text(it, color = Color(0xFF2E6B2E)) }
    }
}