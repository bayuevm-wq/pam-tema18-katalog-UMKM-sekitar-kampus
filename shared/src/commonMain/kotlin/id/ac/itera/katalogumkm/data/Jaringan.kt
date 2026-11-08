package id.ac.itera.katalogumkm.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Alamat API kelompok, TANPA garis miring di akhir. Cukup ubah di sini kalau server pindah.
// Pilihan A (my-json-server, membaca db.json dari repo GitHub ini):
const val BASE_URL = "https://my-json-server.typicode.com/bayuevm-wq/pam-tema18-katalog-UMKM-sekitar-kampus"
// Pilihan B (mockapi.io, buat proyek sendiri lalu tempel alamatnya), contoh:
// const val BASE_URL = "https://xxxxxxxx.mockapi.io/api/v1"

// Dibuat SATU kali untuk seluruh aplikasi (dipanggil dari App.kt)
fun buatClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true   // kunci JSON tambahan diabaikan
            isLenient = true           // terima angka bertanda kutip, mis. id "13" dari mockapi
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 10_000  // menyerah setelah 10 detik
    }
    expectSuccess = true               // kode selain 2xx dianggap error
}
