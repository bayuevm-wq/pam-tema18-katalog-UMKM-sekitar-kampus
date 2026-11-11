
package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val produk by vm.produk.collectAsState()
    val pesan by vm.pesan.collectAsState()
    val menyegarkan by vm.menyegarkan.collectAsState()

    Column(Modifier.fillMaxSize()) {

        // Menampilkan pesan kecil ketika offline
        if (produk.isNotEmpty()) {
            pesan?.let { teks ->
                BannerOffline(
                    teks,
                    onCobaLagi = vm::segarkan
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                // Jika database berisi produk, tampilkan daftar
                produk.isNotEmpty() ->
                    LayarKatalog(
                        semua = produk,
                        umkm = umkm,
                        onTambah = onTambah,
                        onPilih = onPilih
                    )

                // Jika sedang mengambil data, tampilkan loading
                menyegarkan ->
                    CircularProgressIndicator()

                // Jika database kosong dan gagal mengambil data
                else ->
                    PesanGagal(
                        pesan = pesan ?: "Belum ada produk.",
                        onCobaLagi = vm::segarkan
                    )
            }
        }
    }
}

// Pita pemberitahuan ketika offline
@Composable
fun BannerOffline(
    pesan: String,
    onCobaLagi: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF6EDCF))
            .padding(
                horizontal = 12.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            pesan,
            Modifier.weight(1f)
        )

        TextButton(onClick = onCobaLagi) {
            Text("Coba lagi")
        }
    }
}

// Pesan ketika data belum tersedia
@Composable
fun PesanGagal(
    pesan: String,
    onCobaLagi: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            pesan,
            textAlign = TextAlign.Center
        )

        Button(onClick = onCobaLagi) {
            Text("Coba lagi")
        }
    }
}
