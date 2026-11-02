package id.ac.itera.katalogumkm.nav

import kotlinx.serialization.Serializable

// Lima rute = lima layar wajib Bank Proyek.
// Rute tanpa data cukup object; rute yang membawa data memakai data class.
@Serializable object RuteKatalog                 // daftar produk (tab, layar pertama)
@Serializable data class RuteDetail(val id: Int) // detail satu produk, membawa id
@Serializable object RuteForm                    // tambah produk
@Serializable object RuteAsisten                 // fitur AI (tab), diisi Modul 9
@Serializable object RuteProfil                  // profil (tab)
