package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.itera.katalogumkm.model.StatusProduk

@Composable
fun LabelStatus(status: StatusProduk) {
    // when tanpa else: kalau nanti ada status baru, compiler langsung protes di sini
    val (teks, latar, warnaTeks) = when (status) {
        is StatusProduk.Tersedia -> Triple("Tersedia", Color(0xFFE3F1DF), Color(0xFF2E6B2E))
        is StatusProduk.PreOrder -> Triple("Pre-order", Color(0xFFDCE7F7), Color(0xFF1F4E8C))
        StatusProduk.Habis -> Triple("Habis", Color(0xFFF6DADA), Color(0xFF8C1515))
    }
    Text(
        text = teks,
        color = warnaTeks,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(latar, RoundedCornerShape(50))   // warna dulu...
            .padding(horizontal = 12.dp, vertical = 4.dp) // ...baru jarak di dalamnya
    )
}
