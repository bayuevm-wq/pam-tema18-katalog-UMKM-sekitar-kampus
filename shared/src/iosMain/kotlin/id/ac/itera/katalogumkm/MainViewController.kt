package id.ac.itera.katalogumkm

import androidx.compose.ui.window.ComposeUIViewController
import id.ac.itera.katalogumkm.data.DriverFactory

fun MainViewController() = ComposeUIViewController { App(DriverFactory()) }
