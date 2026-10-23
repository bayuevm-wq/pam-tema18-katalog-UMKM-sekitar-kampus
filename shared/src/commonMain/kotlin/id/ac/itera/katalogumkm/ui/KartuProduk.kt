package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import id.ac.itera.katalogumkm.model.Produk

@Composable
fun KartuProduk(
    produk: Produk,          // wajib diisi
    namaUmkm: String,        // nama warung penjualnya
    onKlik: () -> Unit = {}  // dipanggil saat kartu diketuk
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onKlik() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = produk.nama,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = namaUmkm, color = Color.Gray)
                Text(text = "${formatRupiah(produk.harga)} · ${produk.kategori}")
            }
            Spacer(modifier = Modifier.width(8.dp))
            LabelStatus(produk.status)
        }
    }
}
