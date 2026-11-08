package id.ac.itera.katalogumkm.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Alamat API kelompok, TANPA garis miring di akhir. Cukup ubah di sini kalau server pindah.
// Pilihan B (mockapi.io) dipakai karena my-json-server sedang tidak bisa dijangkau:
const val BASE_URL = "https://6af0821911a6aa866e7f89f0.mockapi.io/api/v1"
// Pilihan A (my-json-server, membaca db.json dari repo GitHub ini):
// const val BASE_URL = "https://my-json-server.typicode.com/bayuevm-wq/pam-tema18-katalog-UMKM-sekitar-kampus"

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
