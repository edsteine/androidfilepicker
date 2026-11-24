package com.edsteine.androidfilepicker

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.edsteine.androidfilepicker.components.PermissionButton
import com.edsteine.androidfilepicker.components.PermissionSection
import com.edsteine.androidfilepicker.permissions.contracts.PermissionType
import com.edsteine.androidfilepicker.permissions.utils.AndroidVersionUtils

/**
 * Demonstrates the usage of the permission library, displaying buttons to request various
 * Android permissions.
 *
 * This screen utilizes [PermissionSection] to group permissions and [PermissionButton] for each
 * individual permission request.
 *
 * @param navController The navigation controller to handle screen navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    context as ComponentActivity

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Android Permissions Demo",
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Permissions Sections
            PermissionSection(title = "Network & Connectivity", icon = Icons.Filled.SettingsEthernet){
                PermissionType.INTERNET.PermissionButton(title = "Internet")
                PermissionType.ACCESS_NETWORK_STATE.PermissionButton(title = "Access Network State")
                PermissionType.ACCESS_WIFI_STATE.PermissionButton(title = "Access Wifi State")
                if (AndroidVersionUtils.isAtLeastP()) {
                    PermissionType.FOREGROUND_SERVICE.PermissionButton(title = "Foreground Service")
                }
                PermissionType.CHANGE_WIFI_STATE.PermissionButton(title = "Change Wifi State")
                PermissionType.CHANGE_NETWORK_STATE.PermissionButton(title = "Change Network State")
                PermissionType.CHANGE_WIFI_MULTICAST_STATE.PermissionButton(title = "Change Wifi Multicast State")
            }

            PermissionSection(
                title = "Location Services",
                icon = Icons.Default.LocationOn
            ) {
                PermissionType.FINE_LOCATION.PermissionButton(title = "Fine Location")
                PermissionType.COARSE_LOCATION.PermissionButton(title = "Coarse Location")
                if (AndroidVersionUtils.isAtLeastQ()) {
                    PermissionType.BACKGROUND_LOCATION.PermissionButton(title = "Background Location")
                }
            }

            PermissionSection(
                title = "Camera and Media",
                icon = Icons.Filled.Camera,
            ) {
                PermissionType.CAMERA.PermissionButton(title = "Camera")
                if (AndroidVersionUtils.isAtLeastTiramisu()) {
                    PermissionType.READ_IMAGES.PermissionButton(title = "Read Images")
                    PermissionType.READ_VIDEOS.PermissionButton(title = "Read Videos")
                    PermissionType.READ_AUDIO.PermissionButton(title = "Read Audio")
                } else {
                    PermissionType.READ_STORAGE.PermissionButton(title = "Read Storage")
                    PermissionType.WRITE_STORAGE.PermissionButton(title = "Write Storage")
                }
                if (AndroidVersionUtils.isAtLeastUpsideDownCake()) {
                    PermissionType.READ_IMAGES_VIDEO_VISUAL_USER_SELECTED.PermissionButton(title = "Read Visual User Selected")
                }
                PermissionType.RECORD_AUDIO.PermissionButton(title = "Record Audio")
            }


            PermissionSection(
                title = "Bluetooth",
                icon = Icons.Default.Bluetooth,
            ) {
                PermissionType.BLUETOOTH_ADMIN.PermissionButton(title = "Bluetooth Admin")
                if (AndroidVersionUtils.isAtLeastS()) {
                    PermissionType.BLUETOOTH_CONNECT.PermissionButton(title = "Bluetooth Connect")
                }
            }

            PermissionSection(
                title = "Contacts",
                icon = Icons.Default.Contacts,
            ) {
                PermissionType.READ_CONTACTS.PermissionButton(title = "Read Contacts")
                PermissionType.WRITE_CONTACTS.PermissionButton(title = "Write Contacts")
                PermissionType.GET_ACCOUNTS.PermissionButton(title = "Get Accounts")
            }

            PermissionSection(
                title = "Phone",
                icon = Icons.Default.Phone,
            ) {
                PermissionType.READ_PHONE_STATE.PermissionButton(title = "Read Phone State")
                PermissionType.CALL_PHONE.PermissionButton(title = "Call Phone")
                PermissionType.ANSWER_PHONE_CALLS.PermissionButton(title = "Answer Phone Calls")
                PermissionType.READ_PHONE_NUMBERS.PermissionButton(title = "Read Phone Numbers")
                PermissionType.USE_SIP.PermissionButton(title = "Use SIP")
                PermissionType.WRITE_CALL_LOG.PermissionButton(title = "Write Call Log")
                PermissionType.READ_CALL_LOG.PermissionButton(title = "Read Call Log")
            }

            PermissionSection(
                title = "SMS",
                icon = Icons.Default.Sms,
            ) {
                PermissionType.SEND_SMS.PermissionButton(title = "Send SMS")
                PermissionType.RECEIVE_SMS.PermissionButton(title = "Receive SMS")
                PermissionType.RECEIVE_MMS.PermissionButton(title = "Receive MMS")
                PermissionType.READ_SMS.PermissionButton(title = "Read SMS")
                PermissionType.RECEIVE_WAP_PUSH.PermissionButton(title = "Receive Wap Push")
            }

            PermissionSection(
                title = "Calendar",
                icon = Icons.Default.DateRange,
            ) {
                PermissionType.READ_CALENDAR.PermissionButton(title = "Read Calendar")
                PermissionType.WRITE_CALENDAR.PermissionButton(title = "Write Calendar")
            }

            PermissionSection(
                title = "Sensors",
                icon = Icons.Default.Sensors,
            ) {
                PermissionType.BODY_SENSORS.PermissionButton(title = "Body Sensors")
                if (AndroidVersionUtils.isAtLeastTiramisu()) {
                    PermissionType.BODY_SENSORS_BACKGROUND.PermissionButton(title = "Body Sensors Background")
                }
            }

            if (AndroidVersionUtils.isAtLeastQ()) {
                PermissionSection(
                    title = "Activity Recognition",
                    icon = Icons.Filled.PrecisionManufacturing,
                ) {
                    PermissionType.ACTIVITY_RECOGNITION.PermissionButton(title = "Activity Recognition")
                }
            }


            if (AndroidVersionUtils.isAtLeastTiramisu()) {
                PermissionSection(
                    title = "Notifications",
                    icon = Icons.Default.Notifications,
                ) {
                    PermissionType.POST_NOTIFICATIONS.PermissionButton(title = "Post Notifications")
                }
            }


            PermissionSection(
                title = "System and Alerts",
                icon = Icons.Filled.Settings
            ) {
                PermissionType.SYSTEM_ALERT_WINDOW.PermissionButton(title = "System Alert Window")
                PermissionType.REQUEST_INSTALL_PACKAGES.PermissionButton(title = "Request Install Packages")
                PermissionType.ACCESS_NOTIFICATION_POLICY.PermissionButton(title = "Access Notification Policy")
            }

            PermissionSection(
                title = "Additional Permissions",
                icon = Icons.AutoMirrored.Filled.More,
            ) {
                PermissionType.WAKE_LOCK.PermissionButton(title = "Wake Lock")
                PermissionType.NFC.PermissionButton(title = "NFC")
                if (AndroidVersionUtils.isAtLeastVanillaIceCream()) {
                    PermissionType.ACCESS_HIDDEN_PROFILES.PermissionButton(title = "Access Hidden Profiles")
                }
                PermissionType.ACCESS_LOCATION_EXTRA_COMMANDS.PermissionButton(title = "Access Location Extra Commands")
                PermissionType.GET_PACKAGE_SIZE.PermissionButton(title = "Get Package Size")
            }


            LogAllPermissions()
        }
    }
}
/**
 * Composable that logs all the permissions and also opens a new activity to show all of them.
 */
@Composable
fun LogAllPermissions() {
    val context = LocalContext.current

    Button(onClick = {
        val intent = android.content.Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }) {
        Text("Show App Permissions Status")
    }
}