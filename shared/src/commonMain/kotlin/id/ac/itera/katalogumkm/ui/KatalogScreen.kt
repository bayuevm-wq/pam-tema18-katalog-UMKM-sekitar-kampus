package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.ac.itera.katalogumkm.model.Umkm

@Composable
fun KatalogScreen(
    vm: KatalogViewModel,
    umkm: List<Umkm>,
    onTambah: () -> Unit,
    onPilih: (Int) -> Unit
) {
    val state by vm.uiState.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val s = state) {
            KatalogUiState.Memuat ->
                CircularProgressIndicator()

            is KatalogUiState.Berhasil ->
                LayarKatalog(semua = s.produk, umkm = umkm, onTambah = onTambah, onPilih = onPilih)

            is KatalogUiState.Gagal ->
                PesanGagal(pesan = s.pesan, onCobaLagi = vm::muat)
        }
    }
}

@Composable
fun PesanGagal(pesan: String, onCobaLagi: () -> Unit) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(pesan, textAlign = TextAlign.Center)
        Button(onClick = onCobaLagi) { Text("Coba lagi") }
    }
}