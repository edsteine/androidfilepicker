package com.edsteine.androidfilepicker.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun PermissionDialog(
    permissionList: List<String>,
    onDismiss: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val transformed = permissionList.map { it.substringAfterLast('.') }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Permission Required") },
        text = {
            Text(
                "Please enable $transformed permission in Settings to use this feature."
            )
        },
        confirmButton = {
            Button(onClick = onOpenSettings) {
                Text("Open Settings")
            }

        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}