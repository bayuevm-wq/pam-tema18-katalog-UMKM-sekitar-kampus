package id.ac.itera.katalogumkm.model

fun StatusProduk.label(): String = when (this) {
    is StatusProduk.Tersedia -> "Tersedia (stok $stok)"
    is StatusProduk.PreOrder -> "Pre-order, siap $estimasiHari hari lagi"
    StatusProduk.Habis -> "Stok habis"
}

fun Produk.ringkas(): String = "#$id $nama - Rp$harga"
