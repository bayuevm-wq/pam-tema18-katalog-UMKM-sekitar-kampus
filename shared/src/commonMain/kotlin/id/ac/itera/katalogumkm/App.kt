package id.ac.itera.katalogumkm

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.ac.itera.katalogumkm.data.ProdukApi
import id.ac.itera.katalogumkm.data.Sumber
import id.ac.itera.katalogumkm.data.SumberProdukJaringan
import id.ac.itera.katalogumkm.data.buatClient
import id.ac.itera.katalogumkm.data.contohUmkm
import id.ac.itera.katalogumkm.model.Produk
import id.ac.itera.katalogumkm.nav.RuteAsisten
import id.ac.itera.katalogumkm.nav.RuteDetail
import id.ac.itera.katalogumkm.nav.RuteForm
import id.ac.itera.katalogumkm.nav.RuteKatalog
import id.ac.itera.katalogumkm.nav.RuteProfil
import id.ac.itera.katalogumkm.nav.TAB
import id.ac.itera.katalogumkm.nav.pindahTab
import id.ac.itera.katalogumkm.ui.DetailViewModel
import id.ac.itera.katalogumkm.ui.FormProdukScreen
import id.ac.itera.katalogumkm.ui.FormProdukViewModel
import id.ac.itera.katalogumkm.ui.KatalogScreen
import id.ac.itera.katalogumkm.ui.KatalogViewModel
import id.ac.itera.katalogumkm.ui.LayarAsisten
import id.ac.itera.katalogumkm.ui.LayarDetail
import id.ac.itera.katalogumkm.ui.LayarProfil

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Inisialisasi sumber jaringan & KatalogViewModel tugas Natan
        val sumber: Sumber<Produk> = remember { SumberProdukJaringan(ProdukApi(buatClient())) }
        val katalogVm = viewModel { KatalogViewModel(sumber) }

        val nav = rememberNavController()

        Scaffold(
            bottomBar = {
                NavigationBar {
                    val entry by nav.currentBackStackEntryAsState()
                    TAB.forEach { tab ->
                        NavigationBarItem(
                            selected = entry?.destination?.hasRoute(tab.rute::class) == true,
                            onClick = { pindahTab(nav, tab.rute) },
                            icon = { Text(tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = nav,
                startDestination = RuteKatalog,
                modifier = Modifier.padding(padding).consumeWindowInsets(padding)
            ) {
                composable<RuteKatalog> {
                    KatalogScreen(
                        vm = katalogVm,
                        umkm = contohUmkm,
                        onTambah = { nav.navigate(RuteForm) },
                        onPilih = { id -> nav.navigate(RuteDetail(id)) }
                    )
                }
                composable<RuteDetail> { entry ->
                    val id = entry.toRoute<RuteDetail>().id
                    val vm = viewModel { DetailViewModel(id) }
                    val state by vm.uiState.collectAsState()
                    LayarDetail(
                        state = state,
                        kembali = { nav.popBackStack() }
                    )
                }
                composable<RuteForm> {
                    FormProdukScreen(
                        umkm = contohUmkm,
                        onKembali = { nav.popBackStack() },
                        onTersimpan = {
                            katalogVm.muat()
                            nav.popBackStack()
                        },
                        vm = viewModel { FormProdukViewModel() }
                    )
                }
                composable<RuteAsisten> { LayarAsisten() }
                composable<RuteProfil> { LayarProfil() }
            }
        }
    }
}