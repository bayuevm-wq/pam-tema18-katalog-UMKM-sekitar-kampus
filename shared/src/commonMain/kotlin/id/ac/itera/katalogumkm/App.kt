package id.ac.itera.katalogumkm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.ac.itera.katalogumkm.data.PenyimpananProduk
import id.ac.itera.katalogumkm.data.contohUmkm
import id.ac.itera.katalogumkm.nav.RuteAsisten
import id.ac.itera.katalogumkm.nav.RuteDetail
import id.ac.itera.katalogumkm.nav.RuteForm
import id.ac.itera.katalogumkm.nav.RuteKatalog
import id.ac.itera.katalogumkm.nav.RuteProfil
import id.ac.itera.katalogumkm.nav.TAB
import id.ac.itera.katalogumkm.nav.pindahTab
import id.ac.itera.katalogumkm.ui.DetailViewModel
import id.ac.itera.katalogumkm.ui.FormProdukScreen
import id.ac.itera.katalogumkm.ui.LayarAsisten
import id.ac.itera.katalogumkm.ui.LayarDetail
import id.ac.itera.katalogumkm.ui.LayarKatalog
import id.ac.itera.katalogumkm.ui.LayarProfil

@Composable
@Preview
fun App() {
    MaterialTheme {
        val nav = rememberNavController()   // pengemudi

        Scaffold(
            bottomBar = {
                NavigationBar {
                    // Tahu layar mana yang aktif, supaya tab yang benar tampil terpilih
                    val entry by nav.currentBackStackEntryAsState()
                    TAB.forEach { tab ->
                        val aktif = entry?.destination?.hasRoute(tab.rute::class) == true
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp)
                                .clickable { pindahTab(nav, tab.rute) },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = tab.label,
                                    fontSize = 16.sp,
                                    fontWeight = if (aktif) FontWeight.Bold else FontWeight.Medium,
                                    color = if (aktif) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.height(6.dp))
                                // Garis penanda tab aktif
                                Box(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(3.dp)
                                        .background(
                                            if (aktif) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            RoundedCornerShape(50)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        ) { padding ->
            // Peta: semua rute dan layar yang digambar untuk tiap rute
            NavHost(
                navController = nav,
                startDestination = RuteKatalog,
                modifier = Modifier.padding(padding).consumeWindowInsets(padding)
            ) {
                composable<RuteKatalog> {
                    val daftar by PenyimpananProduk.daftar.collectAsState()
                    // Layar tidak kenal nav, hanya mengirim event ke atas
                    LayarKatalog(
                        semua = daftar,
                        umkm = contohUmkm,
                        onTambah = { nav.navigate(RuteForm) },
                        onPilih = { id -> nav.navigate(RuteDetail(id)) }
                    )
                }
                composable<RuteDetail> { entry ->
                    val id = entry.toRoute<RuteDetail>().id   // baca id dari rute
                    val vm = viewModel { DetailViewModel(id) }
                    val state by vm.uiState.collectAsState()
                    LayarDetail(state = state, kembali = { nav.popBackStack() })
                }
                composable<RuteForm> {
                    FormProdukScreen(
                        umkm = contohUmkm,
                        onKembali = { nav.popBackStack() },
                        onTersimpan = { nav.popBackStack() }   // form dibuang, kembali ke katalog
                    )
                }
                composable<RuteAsisten> { LayarAsisten() }
                composable<RuteProfil> { LayarProfil() }
            }
        }
    }
}
