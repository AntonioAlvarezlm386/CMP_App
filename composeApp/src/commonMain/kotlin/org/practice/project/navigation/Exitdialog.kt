package org.practice.project.navigation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


// ExitDialog.kt
@Composable
fun ExitConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Cerrar sesión")
        },
        text = {
            Text("¿Estás seguro de que quieres cerrar sesión?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Sí, cerrar sesión")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}