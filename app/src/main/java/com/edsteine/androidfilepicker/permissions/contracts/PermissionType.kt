package com.edsteine.androidfilepicker.permissions.contracts

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Defines constants for various Android permissions.
 *
 * This object provides a convenient way to access permission strings, grouped by functionality.
 */
sealed class PermissionType(val permissions: List<String>) {

    /**
     * Network and Connectivity permissions.
     */
    object INTERNET : PermissionType(listOf(Manifest.permission.INTERNET))
    object ACCESS_NETWORK_STATE : PermissionType(listOf(Manifest.permission.ACCESS_NETWORK_STATE))
    object ACCESS_WIFI_STATE : PermissionType(listOf(Manifest.permission.ACCESS_WIFI_STATE))

    @RequiresApi(Build.VERSION_CODES.P)
    object FOREGROUND_SERVICE : PermissionType(listOf(Manifest.permission.FOREGROUND_SERVICE))
    object CHANGE_WIFI_STATE : PermissionType(listOf(Manifest.permission.CHANGE_WIFI_STATE))
    object CHANGE_NETWORK_STATE : PermissionType(listOf(Manifest.permission.CHANGE_NETWORK_STATE))
    object CHANGE_WIFI_MULTICAST_STATE : PermissionType(listOf(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE))

    /**
     * Location permissions.
     */
    object FINE_LOCATION : PermissionType(listOf(Manifest.permission.ACCESS_FINE_LOCATION))
    object COARSE_LOCATION : PermissionType(listOf(Manifest.permission.ACCESS_COARSE_LOCATION))

    @RequiresApi(Build.VERSION_CODES.Q)
    object BACKGROUND_LOCATION : PermissionType(listOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))

    /**
     * Camera and Media permissions.
     */
    object CAMERA : PermissionType(listOf(Manifest.permission.CAMERA))

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object READ_IMAGES : PermissionType(listOf(Manifest.permission.READ_MEDIA_IMAGES))
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object READ_VIDEOS : PermissionType(listOf(Manifest.permission.READ_MEDIA_VIDEO))

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    object READ_IMAGES_VIDEO_VISUAL_USER_SELECTED : PermissionType(
        listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
        )
    )
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object READ_AUDIO : PermissionType(listOf(Manifest.permission.READ_MEDIA_AUDIO))

    object RECORD_AUDIO : PermissionType(listOf(Manifest.permission.RECORD_AUDIO))

    object READ_STORAGE : PermissionType(listOf(Manifest.permission.READ_EXTERNAL_STORAGE))
    object WRITE_STORAGE : PermissionType(listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))

    /**
     * Bluetooth permissions.
     */
    object BLUETOOTH_ADMIN : PermissionType(listOf(Manifest.permission.BLUETOOTH_ADMIN))

    @RequiresApi(Build.VERSION_CODES.S)
    object BLUETOOTH_CONNECT : PermissionType(
        listOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE
        )
    )

    /**
     * Contacts permissions.
     */
    object READ_CONTACTS : PermissionType(listOf(Manifest.permission.READ_CONTACTS))
    object WRITE_CONTACTS : PermissionType(listOf(Manifest.permission.WRITE_CONTACTS))
    object GET_ACCOUNTS : PermissionType(listOf(Manifest.permission.GET_ACCOUNTS))

    /**
     * Phone permissions.
     */
    object READ_PHONE_STATE : PermissionType(listOf(Manifest.permission.READ_PHONE_STATE))
    object CALL_PHONE : PermissionType(listOf(Manifest.permission.CALL_PHONE))
    object ANSWER_PHONE_CALLS : PermissionType(listOf(Manifest.permission.ANSWER_PHONE_CALLS))
    object READ_PHONE_NUMBERS : PermissionType(listOf(Manifest.permission.READ_PHONE_NUMBERS))

    object USE_SIP : PermissionType(listOf(Manifest.permission.USE_SIP))
    object WRITE_CALL_LOG : PermissionType(listOf(Manifest.permission.WRITE_CALL_LOG))
    object READ_CALL_LOG : PermissionType(listOf(Manifest.permission.READ_CALL_LOG))


    /**
     * SMS permissions.
     */
    object SEND_SMS : PermissionType(listOf(Manifest.permission.SEND_SMS))
    object RECEIVE_SMS : PermissionType(listOf(Manifest.permission.RECEIVE_SMS))
    object RECEIVE_MMS : PermissionType(listOf(Manifest.permission.RECEIVE_MMS))
    object READ_SMS : PermissionType(listOf(Manifest.permission.READ_SMS))
    object RECEIVE_WAP_PUSH : PermissionType(listOf(Manifest.permission.RECEIVE_WAP_PUSH))

    /**
     * Calendar permissions.
     */
    object READ_CALENDAR : PermissionType(listOf(Manifest.permission.READ_CALENDAR))
    object WRITE_CALENDAR : PermissionType(listOf(Manifest.permission.WRITE_CALENDAR))


    /**
     * Sensor permissions.
     */
    object BODY_SENSORS : PermissionType(listOf(Manifest.permission.BODY_SENSORS))

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object BODY_SENSORS_BACKGROUND : PermissionType(listOf(Manifest.permission.BODY_SENSORS_BACKGROUND))

    /**
     * Activity Recognition permission.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    object ACTIVITY_RECOGNITION : PermissionType(listOf(Manifest.permission.ACTIVITY_RECOGNITION))

    /**
     * Notifications permission.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    object POST_NOTIFICATIONS : PermissionType(listOf(Manifest.permission.POST_NOTIFICATIONS))

    /**
     * System and Alerts permissions.
     */
    object SYSTEM_ALERT_WINDOW : PermissionType(listOf(Manifest.permission.SYSTEM_ALERT_WINDOW))
    object REQUEST_INSTALL_PACKAGES : PermissionType(listOf(Manifest.permission.REQUEST_INSTALL_PACKAGES))
    object ACCESS_NOTIFICATION_POLICY : PermissionType(listOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY))

    /**
     * Additional permissions.
     */
    object WAKE_LOCK : PermissionType(listOf(Manifest.permission.WAKE_LOCK))
    object NFC : PermissionType(listOf(Manifest.permission.NFC))

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    object ACCESS_HIDDEN_PROFILES : PermissionType(listOf(Manifest.permission.ACCESS_HIDDEN_PROFILES))
    object ACCESS_LOCATION_EXTRA_COMMANDS : PermissionType(listOf(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS))
    object GET_PACKAGE_SIZE : PermissionType(listOf(Manifest.permission.GET_PACKAGE_SIZE))
}