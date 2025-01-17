package com.edsteine.androidfilepicker.permissions.viewmodel

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsteine.androidfilepicker.permissions.models.PermissionResult
import com.edsteine.androidfilepicker.permissions.utils.PermissionUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PermissionViewModel(private val permissionList: List<String>) : ViewModel() {
    private val _permissionState = MutableStateFlow<PermissionResult>(PermissionResult.Requested)
    val permissionState = _permissionState.asStateFlow()

    // For checking permission state without requesting
    fun checkPermissionState(activity: ComponentActivity) {
        viewModelScope.launch {
            val sharedPreferences = activity.getSharedPreferences("permissions", Context.MODE_PRIVATE)

            val hasRequestedBefore = permissionList.any {
                sharedPreferences.getBoolean("has_requested_$it", false)
            }

            val result =  PermissionUtils.checkPermissionState(
                context = activity,
                permissionList = permissionList,
                hasRequestedBefore = hasRequestedBefore,
                isPermissionRevokedByPolicy = { permission ->
                    activity.packageManager.isPermissionRevokedByPolicy(permission, activity.packageName)
                }
            )
            _permissionState.value = result
        }
    }

    // For handling actual permission request
    fun handlePermissionRequest(activity: ComponentActivity) {
        viewModelScope.launch {
            permissionList.forEach { permission ->
                activity.getSharedPreferences("permissions", Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean("has_requested_$permission", true)
                    .apply()
            }

            val result = PermissionUtils.checkPermissionState(
                context = activity,
                permissionList = permissionList,
                hasRequestedBefore = true,
                isPermissionRevokedByPolicy = { permission ->
                    activity.packageManager.isPermissionRevokedByPolicy(permission, activity.packageName)
                }
            )
            _permissionState.value = result

        }
    }
}