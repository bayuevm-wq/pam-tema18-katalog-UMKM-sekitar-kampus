package id.ac.itera.katalogumkm.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun KolomTeks(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    angka: Boolean = false,
    pesanError: String? = null
) {
    val teksError: (@Composable () -> Unit)? =
        pesanError?.let { pesan -> @Composable { Text(pesan) } }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = pesanError != null,
        supportingText = teksError,
        keyboardOptions = if (angka) KeyboardOptions(keyboardType = KeyboardType.Number)
        else KeyboardOptions.Default,
        modifier = Modifier.fillMaxWidth()
    )
}