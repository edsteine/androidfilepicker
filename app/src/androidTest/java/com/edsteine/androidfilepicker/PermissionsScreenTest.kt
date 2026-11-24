package com.edsteine.androidfilepicker

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.edsteine.androidfilepicker.permissions.contracts.PermissionType
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import com.edsteine.androidfilepicker.permissions.viewmodel.PermissionViewModel
import androidx.compose.ui.test.hasClickAction
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith
import android.Manifest
/**
 * Instrumented test class for testing the PermissionsScreen.
 *
 * This class uses Compose testing APIs to verify the UI elements and interactions within the
 * PermissionsScreen.
 */
@RunWith(AndroidJUnit4::class)
class PermissionsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel : PermissionViewModel

    @BeforeEach
    fun setup(){
        viewModel = PermissionViewModel(listOf(Manifest.permission.CAMERA))
    }

    /**
     * Tests that all permission buttons are displayed in the PermissionsScreen.
     *
     * This test iterates over all permission types and asserts that a button with the
     * corresponding text is present on the screen.
     */
    @Test
    fun testAllPermissionsButtonsAreDisplayed() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            PermissionsScreen(navController)
        }

        getAllPermissionNames().forEach { permissionName ->
            composeTestRule.onNodeWithText("Request Permission For $permissionName").assertExists()
        }
    }

    /**
     * Tests that clicking each permission button triggers a request.
     *
     * This test iterates over all permission types, clicks the corresponding button, and then
     * asserts that the button is still clickable to verify that a request has been triggered.
     *
     * Note: the button will be disabled after the permissions are granted.
     */
    @Test
    fun testClickEachPermissionButtonTriggersRequest() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            PermissionsScreen(navController)
        }

        getAllPermissionNames().forEach { permissionName ->
            val buttonText = "Request Permission For $permissionName"
            composeTestRule.onNodeWithText(buttonText).assertExists()
            composeTestRule.onNodeWithText(buttonText).performClick()
            composeTestRule.onNodeWithText(buttonText).assert(hasClickAction())
        }
    }

    /**
     * Tests that clicking the 'Show App Permissions Status' button navigates to the Permissions Status screen.
     *
     * This test checks if the button is displayed, clicks the button, and then verifies
     * that the 'Permissions Status' text is present on the new screen.
     */
    @Test
    fun testShowPermissionsStatusButton() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            PermissionsScreen(navController)
        }
        composeTestRule.onNodeWithText("Show App Permissions Status").assertExists()
        composeTestRule.onNodeWithText("Show App Permissions Status").performClick()
        composeTestRule.onNodeWithText("Permissions Status").assertExists()

    }


//    private fun getAllPermissionNames(): List<String> {
//        return  PermissionType::class.java.declaredFields
//            .filter { it.type == List::class.java && it.name != "INSTANCE" }
//            .flatMap { field ->
//                (field.get(PermissionType) as? List<*>)?.filterIsInstance<String>() ?: emptyList()
//            }
//            .distinct()
//            .map { it.substringAfterLast(".") }
//    }
}