package id.ac.itera.katalogumkm

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.ac.itera.katalogumkm.data.contohProduk
import id.ac.itera.katalogumkm.data.contohUmkm
import id.ac.itera.katalogumkm.ui.LayarKatalog

@Composable
@Preview
fun App() {
    MaterialTheme {
        LayarKatalog(semua = contohProduk, umkm = contohUmkm)
    }
}