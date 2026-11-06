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
fun LayarAsisten() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Asisten Belanja", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Nanti kamu bisa bertanya, misalnya: \"makanan di bawah Rp10.000 yang buka malam?\"")
        Text("Fitur AI diisi di Modul 9.", color = Color.Gray)
    }
}