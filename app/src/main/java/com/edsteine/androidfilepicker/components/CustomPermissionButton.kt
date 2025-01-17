package com.edsteine.androidfilepicker.components

import android.Manifest
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.edsteine.androidfilepicker.permissions.contracts.PermissionType
import com.edsteine.androidfilepicker.permissions.models.PermissionResult
import com.edsteine.androidfilepicker.permissions.utils.PermissionUtils
import com.edsteine.androidfilepicker.permissions.viewmodel.PermissionViewModel
import timber.log.Timber

/**
 * Sealed class representing the different states of the permission button.
 */
private sealed class ButtonState {
    object Initial : ButtonState()
    object Requesting : ButtonState()
    object RequiresSettings : ButtonState()
    object Granted : ButtonState()
    object Error : ButtonState()
    object NotDeclared : ButtonState()
    data class NeedPermission(val permission: String) : ButtonState()
}

@Composable
fun CustomPermissionButton(
    permissionList: List<String>,
    title: String
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val viewModel: PermissionViewModel = viewModel(key = permissionList.sorted().joinToString()) {
        PermissionViewModel(permissionList)
    }

    val permissionState by viewModel.permissionState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var buttonState by remember { mutableStateOf<ButtonState>(ButtonState.Initial) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle the permission result
        when {
            permissions.all { it.value } -> {
                // All permissions in this batch were granted
                viewModel.handlePermissionRequest(activity)
            }
            else -> {
                // Some permission was denied
                viewModel.checkPermissionState(activity)
            }
        }
    }
    val singlePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        viewModel.handlePermissionRequest(activity)
    }

    // Initial check of permission state
    LaunchedEffect(Unit) {
        viewModel.checkPermissionState(activity)
    }

    // Update buttonState based on permissionState
    LaunchedEffect(permissionState) {
        buttonState = when (permissionState) {
            PermissionResult.Error -> ButtonState.Error
            PermissionResult.NotDeclared -> ButtonState.NotDeclared
            PermissionResult.Requested -> ButtonState.Requesting
            PermissionResult.RequiresSettings -> ButtonState.RequiresSettings
            PermissionResult.Granted -> ButtonState.Granted
            is PermissionResult.NeedPermission -> ButtonState.NeedPermission((permissionState as PermissionResult.NeedPermission).permission)
        }
    }

    val buttonTextColor by remember {
        derivedStateOf {
            when (buttonState) {
                ButtonState.Error -> Color.Red
                ButtonState.NotDeclared -> Color.Black
                ButtonState.Requesting -> Color.White
                ButtonState.RequiresSettings -> Color.Yellow
                ButtonState.Granted -> Color.Magenta
                is ButtonState.NeedPermission -> Color.DarkGray
                ButtonState.Initial -> Color.White
            }
        }
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        enabled = buttonState != ButtonState.Granted, // Keep button disabled when granted
        onClick = {
            when (buttonState) {
                ButtonState.Error -> {
                    Toast.makeText(context, "Permission error occurred", Toast.LENGTH_SHORT).show()
                    Timber.tag("PermissionButton").e("Error with permission $permissionList")
                }
                ButtonState.NotDeclared -> {
                    Toast.makeText(
                        context,
                        "Permission not declared in manifest",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Timber.tag("PermissionButton").e("Permission not declared $permissionList")
                }
                ButtonState.Requesting -> {
                    permissionLauncher.launch(permissionList.toTypedArray()) //Pass an array as expected
                }
                ButtonState.RequiresSettings -> showDialog = true
                ButtonState.Granted -> {
                    // No need to display Toast when Granted, since the button should be disabled
                    Timber.tag("PermissionButton").d("Permission  Granted For $permissionList")
                }
                is ButtonState.NeedPermission -> {
                    singlePermissionLauncher.launch(arrayOf((buttonState as ButtonState.NeedPermission).permission))
                }
                ButtonState.Initial -> {
                    // Do Nothing
                }
            }
        },
    ) {
        Text(
            text = buttonState.getButtonText(title),
            color = buttonTextColor
        )
    }

    if (showDialog) {
        PermissionDialog(
            permissionList = permissionList,
            onDismiss = { showDialog = false },
            onOpenSettings = {
                Timber.tag("PermissionButton").d("Opening settings for: $title")
                PermissionUtils.openAppSettings(context)
                showDialog = false
            }
        )
    }
}

private fun ButtonState.getButtonText(title: String): String = when (this) {
    ButtonState.Error -> "Permission Error For $title"
    ButtonState.NotDeclared -> "Permission Not Declared for $title"
    ButtonState.Requesting -> "Request Permission For $title"
    ButtonState.RequiresSettings -> "Request Permission From Settings For $title"
    ButtonState.Granted -> "Permission Granted For $title"
    is ButtonState.NeedPermission -> "Need Permission For $title"
    ButtonState.Initial -> "Request Permission For $title"
}

@Preview(showBackground = true)
@Composable
fun CustomPermissionButtonPreview() {
    CustomPermissionButton(permissionList = listOf(Manifest.permission.CAMERA), "CAMERA")
}


/**
 * Extension function for `PermissionType` to create a [CustomPermissionButton].
 *
 * @param title The text to display on the button.
 */
@Composable
fun PermissionType.PermissionButton(title: String) {
    CustomPermissionButton(
        permissionList = this.permissions,
        title = title
    )
}