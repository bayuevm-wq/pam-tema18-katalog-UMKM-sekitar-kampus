package id.ac.itera.katalogumkm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import id.ac.itera.katalogumkm.data.DriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(DriverFactory(applicationContext))   // Android butuh Context untuk membuka database
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(DriverFactory(LocalContext.current))
}