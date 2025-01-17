package com.edsteine.androidfilepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edsteine.androidfilepicker.theme.PermissionsAppTheme
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        setContent {
            PermissionsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "permissions") {
                        composable("permissions") {
                            PermissionsScreen(navController)
                        }
                        composable("permissionsStatus") {
//                            PermissionsStatusScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable that logs all the permissions and also opens a new activity to show all of them.
 */
@Composable
fun LogAllPermissions(navController: NavHostController) {

    Button(onClick = {
        navController.navigate("permissionsStatus")
    }) {
        Text("Show App Permissions Status")
    }

}
