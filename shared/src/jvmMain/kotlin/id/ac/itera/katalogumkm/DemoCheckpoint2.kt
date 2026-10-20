package id.ac.itera.katalogumkm

import id.ac.itera.katalogumkm.data.SumberProdukPalsu
import id.ac.itera.katalogumkm.data.SumberUmkmPalsu
import id.ac.itera.katalogumkm.model.label
import id.ac.itera.katalogumkm.model.ringkas
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val sumberProduk = SumberProdukPalsu()
    val sumberUmkm = SumberUmkmPalsu()

    // 1. Coroutines: ambil produk dan UMKM bersamaan
    val waktu = measureTimeMillis {
        val produk = async { sumberProduk.ambil(1) }
        val umkm = async { sumberUmkm.ambil(1) }
        val u = umkm.await()
        println("${produk.await().ringkas()} | ${u.nama}, ${u.lokasi}")
    }
    println("Waktu muat: $waktu ms")

    // 2. Flow: pantau perubahan status produk
    println("Sebelum collect")
    sumberProduk.pantau(1)
        .map { it.status.label() }
        .collect { println(it) }
    println("Sesudah collect")
}