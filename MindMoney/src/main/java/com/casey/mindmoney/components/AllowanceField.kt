package com.casey.mindmoney.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AllowanceField(label: String) {
    OutlinedTextField(
        value = "",
        onValueChange = { /* placeholder for now */ },
        label = { Text(label, color = MaterialTheme.colorScheme.onSurface) },
        leadingIcon = { Text("$", color = MaterialTheme.colorScheme.onSurface) },
        modifier = Modifier.fillMaxWidth()
    )
}
