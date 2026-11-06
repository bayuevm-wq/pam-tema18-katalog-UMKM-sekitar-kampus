
package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.itera.katalogumkm.model.label

@Composable
fun LayarDetail(state: DetailUiState, kembali: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(onClick = kembali) {
            Text("< Kembali")
        }

        when (state) {
            DetailUiState.Memuat -> CircularProgressIndicator()

            DetailUiState.TidakDitemukan ->
                Text("Produk tidak ditemukan")

            is DetailUiState.Ada -> {
                val p = state.produk

                Text(
                    p.nama,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(formatRupiah(p.harga), fontSize = 20.sp)

                LabelStatus(p.status)
                Text(p.status.label(), color = Color.Gray)

                Text("Kategori: ${p.kategori}")

                if (p.deskripsi.isNotBlank()) {
                    Text(p.deskripsi)
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text("Dijual oleh", fontWeight = FontWeight.Bold)

                val u = state.umkm

                if (u == null) {
                    Text("UMKM tidak dikenal", color = Color.Gray)
                } else {
                    Text(u.nama, fontSize = 18.sp)
                    Text("Pemilik: ${u.pemilik}")
                    Text("Lokasi: ${u.lokasi}")
                    Text("Jam buka: ${u.jamBuka}")
                    Text("WhatsApp: ${u.noWhatsapp ?: "belum dicantumkan"}")
                }
            }
        }
    }
}
