package id.ac.itera.katalogumkm

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import id.ac.itera.katalogumkm.data.PenyimpananProduk
import id.ac.itera.katalogumkm.data.contohUmkm
import id.ac.itera.katalogumkm.ui.FormProdukScreen
import id.ac.itera.katalogumkm.ui.LayarKatalog

@Composable
@Preview
fun App() {
    MaterialTheme {

        var diForm by rememberSaveable {
            mutableStateOf(false)
        }

        val daftar by PenyimpananProduk.daftar.collectAsState()

        if (diForm) {
            FormProdukScreen(
                umkm = contohUmkm,
                onKembali = { diForm = false }
            )
        } else {
            LayarKatalog(
                semua = daftar,
                umkm = contohUmkm,
                onTambah = { diForm = true }
            )
        }
    }
}