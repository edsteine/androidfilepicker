package com.edsteine.androidfilepicker.permissions.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.edsteine.androidfilepicker.permissions.models.PermissionResult

/**
 * Utility object for handling Android runtime permissions.
 */
object PermissionUtils {


    /**
     * Checks if a given permission is granted.
     *
     * @param context The context.
     * @param permission The permission to check.
     * @return True if the permission is granted, false otherwise.
     */
    fun checkPermissionState(context: Context, permissionList: List<String>, hasRequestedBefore: Boolean, isPermissionRevokedByPolicy: (String) -> Boolean): PermissionResult {
        if (context !is Activity) return PermissionResult.Error
        val packageInfo = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_PERMISSIONS
        )
        val declaredPermissions = packageInfo.requestedPermissions ?: return PermissionResult.NotDeclared

        for(permission in permissionList) {
            if (!declaredPermissions.contains(permission)) {
                return PermissionResult.NotDeclared
            }

            if (permission == Manifest.permission.ACCESS_BACKGROUND_LOCATION) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return PermissionResult.NeedPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
            if (permission == Manifest.permission.BODY_SENSORS_BACKGROUND) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.BODY_SENSORS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return   PermissionResult.NeedPermission(Manifest.permission.BODY_SENSORS)
                }
            }
            when (ContextCompat.checkSelfPermission(context, permission)) {
                PackageManager.PERMISSION_GRANTED -> return PermissionResult.Granted
                PackageManager.PERMISSION_DENIED -> {
                    if (!androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        if (hasRequestedBefore && !isPermissionRevokedByPolicy(permission)) {
                            return PermissionResult.RequiresSettings
                        }
                    }
                }
            }
        }
        return PermissionResult.Requested
    }

    /**
     * Opens the app settings screen for the current app.
     *
     * @param context The context.
     */
    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}