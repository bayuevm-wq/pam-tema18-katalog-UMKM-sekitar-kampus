package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import id.ac.itera.katalogumkm.model.Umkm

@Composable
fun LayarKatalog(semua: List<Produk>, umkm: List<Umkm>) {
    val urut = semua.sortedWith(
        compareBy<Produk>({ it.status !is StatusProduk.Tersedia }, { it.harga })
    )
    val jumlahTersedia = semua.count { it.status is StatusProduk.Tersedia }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { /* belum berfungsi, minggu depan */ }) {
                Text("+ Tambah Produk")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Text(
                text = "Katalog UMKM",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            )
            Text(
                text = "$jumlahTersedia dari ${semua.size} produk tersedia",
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            DaftarProduk(
                semua = urut,
                umkm = umkm,
                onPilih = { println("Dipilih: ${it.nama}") }
            )
        }
    }
}