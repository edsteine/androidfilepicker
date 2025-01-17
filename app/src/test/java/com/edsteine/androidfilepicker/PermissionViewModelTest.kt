package com.edsteine.androidfilepicker

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createComposeRule
import com.edsteine.androidfilepicker.permissions.models.PermissionResult
import com.edsteine.androidfilepicker.permissions.viewmodel.PermissionViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.edsteine.androidfilepicker.permissions.contracts.PermissionType
import androidx.core.content.ContextCompat
import android.Manifest
import org.junit.jupiter.api.extension.ExtendWith
import io.mockk.junit5.MockKExtension

@ExtendWith(MockKExtension::class)
class PermissionViewModelTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var activity: ComponentActivity
    private lateinit var context: Context
    private lateinit var packageManager: PackageManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @BeforeEach
    fun setup() {
        composeTestRule.setContent {
            androidx.compose.ui.platform.LocalContext.current
        }

        activity = mockk(relaxed = true)
        context = mockk(relaxed = true)
        packageManager = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true)
        sharedPreferencesEditor = mockk(relaxed = true)

        every { activity.packageManager } returns packageManager
        every { activity.getSharedPreferences(any(), any()) } returns sharedPreferences
        every { sharedPreferences.edit() } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.putBoolean(any(), any()) } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.apply() } returns Unit
        every { activity.applicationContext } returns context
    }

    @Test
    fun `checkPermissionState sets isGranted to true when permission is granted`() = runTest {
        val permissionList = PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)
        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_GRANTED

        viewModel.checkPermissionState(activity)

        assertEquals(PermissionResult.Granted, viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) }
    }


    @Test
    fun `checkPermissionState sets isRequesting when permission is denied`() = runTest {
        val permissionList =  PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)

        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_DENIED
        every { sharedPreferences.getBoolean(any(), any()) } returns false


        viewModel.checkPermissionState(activity)

        assertEquals(PermissionResult.Requested, viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) }

    }

    @Test
    fun `checkPermissionState sets RequiresSettings when permission is denied and has requested before`() = runTest {
        val permissionList =  PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)

        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_DENIED
        every { activity.packageManager.isPermissionRevokedByPolicy(any(), any()) } returns false
        every { sharedPreferences.getBoolean(any(), any()) } returns true


        viewModel.checkPermissionState(activity)

        assertEquals(PermissionResult.RequiresSettings, viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) }


    }

    @Test
    fun `checkPermissionState sets NotDeclared if the permission is not declared`() = runTest {
        val permissionList =  PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)
        val packageManagerMock = mockk<PackageManager>(relaxed = true)
        val activityMock = mockk<ComponentActivity>(relaxed = true) {
            every { packageManager } returns packageManagerMock
            every { packageName } returns "test"
        }
        val requestedPermissions = emptyArray<String>()
        val packageInfo =  mockk<android.content.pm.PackageInfo>(relaxed = true) {
            every { this@mockk.requestedPermissions } returns requestedPermissions
        }

        every { packageManagerMock.getPackageInfo("test", PackageManager.GET_PERMISSIONS) } returns packageInfo


        viewModel.checkPermissionState(activityMock)

        assertEquals(PermissionResult.NotDeclared, viewModel.permissionState.first())
    }



    @Test
    fun `handlePermissionRequest sets isRequesting to true when shouldShowRationale is false`() = runTest {
        val permissionList = PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)

        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_DENIED


        viewModel.handlePermissionRequest(activity)


        assertEquals(PermissionResult.Requested, viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) }

    }

    @Test
    fun `handlePermissionRequest sets isGranted to false when permission is not granted`() = runTest {
        val permissionList = PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)

        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_DENIED


        viewModel.handlePermissionRequest(activity)

        assertEquals(PermissionResult.Requested, viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) }

    }
    @Test
    fun `handlePermissionRequest sets isGranted to true when permission is granted`() = runTest {
        val permissionList = PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)

        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_GRANTED


        viewModel.handlePermissionRequest(activity)
        assertEquals(PermissionResult.Granted, viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) }


    }
    @Test
    fun `checkPermissionState returns Error if context is not an Activity`() = runTest {
        val permissionList = PermissionType.CAMERA.permissions
        val viewModel = PermissionViewModel(permissionList)
        val contextMock = mockk<Context>(relaxed = true)

        viewModel.checkPermissionState(contextMock as ComponentActivity)

        assertEquals(PermissionResult.Error, viewModel.permissionState.first())
    }

    @Test
    fun `checkPermissionState requires fine location if background is requested`() = runTest {
        val permissionList = PermissionType.BACKGROUND_LOCATION.permissions
        val viewModel = PermissionViewModel(permissionList)
        every { ContextCompat.checkSelfPermission(activity,  Manifest.permission.ACCESS_FINE_LOCATION) } returns PackageManager.PERMISSION_DENIED
        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_GRANTED



        viewModel.checkPermissionState(activity)

        assertEquals(PermissionResult.NeedPermission(Manifest.permission.ACCESS_FINE_LOCATION), viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) }


    }

    @Test
    fun `checkPermissionState requires body sensor if background body sensor is requested`() = runTest {
        val permissionList = PermissionType.BODY_SENSORS_BACKGROUND.permissions
        val viewModel = PermissionViewModel(permissionList)

        every { ContextCompat.checkSelfPermission(activity, Manifest.permission.BODY_SENSORS) } returns PackageManager.PERMISSION_DENIED
        every { ContextCompat.checkSelfPermission(activity, permissionList.joinToString()) } returns PackageManager.PERMISSION_GRANTED

        viewModel.checkPermissionState(activity)

        assertEquals(PermissionResult.NeedPermission(Manifest.permission.BODY_SENSORS), viewModel.permissionState.first())
        verify { ContextCompat.checkSelfPermission(activity, Manifest.permission.BODY_SENSORS) }

    }
}