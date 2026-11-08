package id.ac.itera.katalogumkm.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

// Hanya berurusan dengan HTTP. Endpoint memakai kata benda (REST).
//   GET  /produk      -> semua produk
//   GET  /produk/{id} -> satu produk
//   POST /produk      -> tambah produk, server memberi id
class ProdukApi(private val client: HttpClient) {

    suspend fun semua(): List<ProdukDto> =
        client.get("$BASE_URL/produk").body()

    suspend fun ambil(id: Int): ProdukDto =
        client.get("$BASE_URL/produk/$id").body()

    suspend fun kirim(baru: ProdukBaruDto): ProdukDto =
        client.post("$BASE_URL/produk") {
            contentType(ContentType.Application.Json)
            setBody(baru)
        }.body()
}
