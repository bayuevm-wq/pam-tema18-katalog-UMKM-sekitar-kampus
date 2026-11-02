package id.ac.itera.katalogumkm.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

// Pindah tab tanpa menumpuk back stack. Tiga opsi ini selalu dipasang bersama.
fun pindahTab(nav: NavController, rute: Any) {
    nav.navigate(rute) {
        popUpTo(nav.graph.findStartDestination().id) {
            saveState = true          // simpan isi tab yang ditinggal
        }
        launchSingleTop = true        // ketuk tab aktif tidak membuat salinan baru
        restoreState = true           // pulihkan isi dan posisi gulir tab
    }
}
