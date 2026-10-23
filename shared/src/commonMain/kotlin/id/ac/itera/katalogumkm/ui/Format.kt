package id.ac.itera.katalogumkm.ui

// 15000 -> "Rp15.000"
fun formatRupiah(harga: Long): String =
    "Rp" + harga.toString().reversed().chunked(3).joinToString(".").reversed()
