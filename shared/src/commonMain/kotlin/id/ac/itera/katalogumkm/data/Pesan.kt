package id.ac.itera.katalogumkm.data

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException

// Exception -> kalimat yang dimengerti pengguna
fun pesanUntuk(e: Throwable): String = when (e) {
    is HttpRequestTimeoutException ->
        "Server terlalu lama menjawab. Coba lagi."
    is ClientRequestException ->
        if (e.response.status.value == 404) "Produk tidak ditemukan."
        else "Permintaan ditolak server."
    is ServerResponseException ->
        "Server sedang bermasalah. Coba lagi nanti."
    else ->
        "Tidak bisa terhubung. Periksa internet."
}
