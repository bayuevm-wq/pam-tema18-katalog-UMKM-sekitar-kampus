package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.Umkm

@Composable
fun DaftarProduk(
    semua: List<Produk>,
    umkm: List<Umkm>,
    onPilih: (Produk) -> Unit
) {
    val namaUmkm = umkm.associate { it.id to it.nama }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(semua, key = { it.id }) { produk ->
            KartuProduk(
                produk = produk,
                namaUmkm = namaUmkm[produk.umkmId] ?: "UMKM tidak dikenal",
                onKlik = { onPilih(produk) }
            )
        }
    }
}