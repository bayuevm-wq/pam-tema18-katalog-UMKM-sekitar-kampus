package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LayarProfil() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Profil", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Katalog UMKM Sekitar Kampus", fontWeight = FontWeight.Bold)
        Text("Tim pengembang:")
        Text("1. Bayu Setiawan (124140177)")
        Text("2. Arbani Hafizh Saragih (124140139)")
        Text("3. Natan Handani (124140175)")
        Text("Pengaturan akun menyusul.", color = Color.Gray)
    }
}