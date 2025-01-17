//package com.edsteine.androidfilepicker.components
//
//import android.content.Context
//import androidx.activity.ComponentActivity
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.edsteine.androidfilepicker.permissions.models.PermissionResult
//import com.edsteine.androidfilepicker.permissions.utils.PermissionUtils
//import com.edsteine.androidfilepicker.permissions.viewmodel.PermissionViewModel
//
///**
// * A higher-order function to request a list of permissions and handle the result.
// *
// * @param permissions The list of permissions to request.
// * @param onResult A callback that will be invoked with the [PermissionResult]
// */
//@Composable
//fun requestPermissions(
//    permissions: List<String>,
//    onResult: (PermissionResult) -> Unit
//) {
//    val context = LocalContext.current
//    val activity = context as ComponentActivity
//    // Create a unique view model for each permission list, using a sorted key
//    val viewModel: PermissionViewModel = viewModel(key = permissions.sorted().joinToString()) {
//        PermissionViewModel(permissions)
//    }
//    val permissionState by viewModel.permissionState.collectAsStateWithLifecycle()
//
//    // Initial check of permission state
//    LaunchedEffect(Unit) {
//        //This is only needed to update the state if the user
//        //has already granted the permission, and the view model
//        // is created with the permission granted
//        viewModel.checkPermissionState(activity)
//    }
//
//    when (permissionState) {
//        is PermissionResult.Error,
//        is PermissionResult.NotDeclared,
//        is PermissionResult.Granted,
//        is PermissionResult.RequiresSettings,
//        is PermissionResult.Requested -> {
//            onResult(permissionState)
//            // Request permissions if not granted
//
//        }
//    }
//}
//
///**
// * A higher-order function to check the state of a permission.
// *
// * @param permission The permission to check.
// * @return The [PermissionResult].
// */
//@Composable
//fun checkPermissionState(
//    permission: String,
//): PermissionResult {
//    val context = LocalContext.current
//    val activity = context as ComponentActivity
//    val viewModel: PermissionViewModel = viewModel(key = permission) {
//        PermissionViewModel(permission)
//    }
//    val permissionState by viewModel.permissionState.collectAsStateWithLifecycle()
//
//    LaunchedEffect(Unit) {
//        viewModel.checkPermissionState(activity)
//    }
//    return permissionState
//}