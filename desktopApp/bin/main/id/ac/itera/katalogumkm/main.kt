package id.ac.itera.katalogumkm

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "katalog UMKM",
    ) {
        App()
    }
}