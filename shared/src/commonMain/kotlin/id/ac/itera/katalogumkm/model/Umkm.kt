package id.ac.itera.katalogumkm.model

data class Umkm(
    val id: Int,
    val nama: String,
    val pemilik: String,
    val lokasi: String,
    val jamBuka: String,
    val noWhatsapp: String?   // boleh kosong: tidak semua UMKM mencantumkan WA
)
