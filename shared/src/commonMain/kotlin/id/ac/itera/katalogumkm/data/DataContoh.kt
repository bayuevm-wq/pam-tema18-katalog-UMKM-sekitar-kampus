package id.ac.itera.katalogumkm.data

import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.model.StatusProduk
import id.ac.itera.katalogumkm.model.Umkm

val contohUmkm = listOf(
    Umkm(1, "Warung Bu Sari", "Sari", "Depan gerbang utama kampus", "07.00-21.00", null),
    Umkm(2, "Kopi Kampus", "Dimas", "Samping asrama mahasiswa", "09.00-23.00", null),
    Umkm(3, "Fotocopy Berkah", "Rahmat", "Dekat gedung kuliah umum", "08.00-20.00", null),
    Umkm(4, "Dapur Mama Rina", "Rina", "Jalan masuk kos-kosan", "10.00-20.00", null)
)

val contohProduk = listOf(
    Produk(1, "Keripik Pisang Coklat", 15000, "Makanan", 1, "Keripik pisang renyah berlumur coklat", null, StatusProduk.Tersedia(10)),
    Produk(2, "Es Kopi Susu Gula Aren", 12000, "Minuman", 2, "Kopi susu dingin, gula aren asli", null, StatusProduk.Tersedia(25)),
    Produk(3, "Jasa Print dan Jilid", 5000, "Jasa", 3, "Print A4 dan jilid lakban", null, StatusProduk.PreOrder(1)),
    Produk(4, "Nasi Ayam Geprek", 13000, "Makanan", 4, "Ayam geprek sambal bawang, level 1-5", null, StatusProduk.Tersedia(8)),
    Produk(5, "Es Teh Jumbo", 5000, "Minuman", 1, "Es teh manis gelas besar", null, StatusProduk.Tersedia(40)),
    Produk(6, "Risol Mayo", 3000, "Makanan", 4, "Risol isi mayo, telur, dan sosis", null, StatusProduk.Tersedia(12)),
    Produk(7, "Matcha Latte", 15000, "Minuman", 2, "Matcha susu dingin", null, StatusProduk.Tersedia(6)),
    Produk(8, "Fotokopi per Lembar", 300, "Jasa", 3, "Fotokopi hitam putih A4/F4", null, StatusProduk.Tersedia(500)),
    Produk(9, "Kue Kering Nastar", 35000, "Makanan", 4, "Nastar selai nanas, toples 250 gram", null, StatusProduk.PreOrder(3)),
    Produk(10, "Mie Goreng Telur", 10000, "Makanan", 1, "Mie goreng dengan telur ceplok", null, StatusProduk.Tersedia(12)),
    Produk(11, "Laminating Kartu", 3000, "Jasa", 3, "Laminating KTM atau kartu ukuran kecil", null, StatusProduk.Habis),
    Produk(12, "Kopi Hitam Tubruk", 6000, "Minuman", 2, "Kopi robusta Lampung", null, StatusProduk.Tersedia(20))
)