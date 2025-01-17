# Android Compose Permission Library

This project contains a set of reusable composable functions for requesting and handling common Android permissions. This is intended to be a simple copy-and-paste library that you can drop into any Android project using Jetpack Compose.

## Contents

*   **`permissions/contracts/PermissionType.kt`:** Defines constants for common Android permissions, categorized by functionality.
*   **`components/CustomPermissionButton.kt`:** A reusable composable function to request permissions and handle their state.
*   **`components/PermissionSection.kt`:** Provides a reusable layout for grouping related permissions.
*   **`components/PermissionDialog.kt`:**  A composable dialog for displaying information when a permission is permanently denied.
*   **`permissions/viewmodel/PermissionViewModel.kt`:** Manages the state of permission requests and updates the UI.
*  **`permissions/utils/PermissionUtils.kt`:** Utility class for handling permission checks and system interactions.
*   **`permissions/models/PermissionResult.kt`:** Defines possible states for permission results.
*   **`PermissionsScreen.kt`:** Demonstrates the usage of the library, displaying a list of permission buttons.
*   **`MainActivity.kt`:** The main activity to launch the application.

## File Structure

```
AndroidFilePicker/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/edsteine/androidfilepicker/
│   │   │   │       ├── MainActivity.kt
│   │   │   │       ├── PermissionsScreen.kt
│   │   │   │       ├── components/
│   │   │   │       │   ├── CustomPermissionButton.kt
│   │   │   │       │   ├── PermissionSection.kt
│   │   │   │       │   └── PermissionDialog.kt
│   │   │   │       ├── permissions/
│   │   │   │       │    ├── contracts/
│   │   │   │       │    │   └── PermissionType.kt
│   │   │   │       │    ├── viewmodel/
│   │   │   │       │    │   └── PermissionViewModel.kt
│   │   │   │       │    ├── utils/
│   │   │   │       │    │   └── PermissionUtils.kt
│   │   │   │       │    └── models/
│   │   │   │       │        └── PermissionResult.kt
│   │   │   │       └── theme/
│   │   │   │           ├── Color.kt
│   │   │   │           ├── Theme.kt
│   │   │   │           └── Type.kt
│   │   │   └── res/
│   │   └── androidTest/
│   │       └── java/
│   │           └── com/edsteine/androidfilepicker/
│   │               └── PermissionsScreenTest.kt
│   │   └── test/
│   │       └── java/
│   │           └── com/edsteine/androidfilepicker/
│   │               └── PermissionViewModelTest.kt
│   └── build.gradle.kts
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       └── gradle-wrapper.properties
├── fastlane/
│   ├── Fastfile
│   ├── gymfile
│   └── README.md
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── local.properties
└── README.md
```

## How to Use

1.  **Copy the `permissions`, `components` and `theme` Folders:** Copy the entire `permissions`, `components`, and `theme` folders into your Android project's `src/main/java/com.edsteine.androidfilepicker/` directory.
2.  **Import the Required Composable:** In your composable functions, import the required permission buttons directly from the `com.edsteine.androidfilepicker.components` package.

    ```kotlin
    import androidx.compose.runtime.Composable
    import com.edsteine.androidfilepicker.components.PermissionButton
    import com.edsteine.androidfilepicker.permissions.contracts.PermissionType
    import androidx.compose.material3.Text
    import androidx.compose.foundation.layout.Column

    @Composable
    fun MyScreen() {
       Column {
       PermissionType.CAMERA.PermissionButton {
         // Use the camera
         Text("Camera permission granted")
       }
      }
    }
    ```

3.  **Manifest:** You still need to declare the permissions you use in your `AndroidManifest.xml`.
    *  **Common Permissions used in the demo:**
     ```xml
        <uses-permission android:name="android.permission.CAMERA" />
        <uses-permission android:name="android.permission.RECORD_AUDIO" />
        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
        <uses-permission android:name="android.permission.READ_CONTACTS" />
        <uses-permission android:name="android.permission.READ_CALENDAR" />
        <uses-permission android:name="android.permission.SEND_SMS" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.BODY_SENSORS" />
     ```
    *  **Important:** You need to make sure to add any other permission you use, or the app will crash. If you are unsure, check the provided `AndroidManifest.xml` on the `app/src/main` directory.

## Error Handling

The library includes proper error handling. When the user denies a permission, it will now display a message in the demo screen using a simple dialog. If a permission is permanently denied, a settings icon will be shown to take the user to the setting screen of the app. The permissions are requested using higher order functions.

**Error Handling Flow:**

1.  **Initial Check:** When a permission button is displayed, the library checks the current state of the permission. If the permission is already granted, the button is disabled.
2.  **Requesting Permissions:** When a permission is needed, the library will use a launcher to prompt the user to grant the permission.
3.  **Rationale Handling:** If the user has previously denied the permission, a rationale dialog will be shown, explaining why it's needed.
4.  **Permanent Denial:** If the user permanently denies the permission, a settings dialog is shown, taking them to the app's settings screen.
5. **Logging:** All states are logged using `Timber` with clear tags, allowing you to easily debug issues in your app.

## Testing

See the `ExampleInstrumentedTest.kt` for a simple UI test that will verify that buttons can be clicked. Unit tests are located in the `PermissionViewModelTest.kt` file which contains tests for the core logic.

## Fastlane

Fastlane can be used in your CI pipeline to automate builds and tests:

### Setup

1.  Install and configure Fastlane in your project. You can follow the instructions on the official Fastlane website: [https://docs.fastlane.tools/](https://docs.fastlane.tools/)
2.   Create a `fastlane` folder at the root of your project.
3.   Inside the `fastlane` folder, create a `Fastfile` and a `gymfile` with the following content:

### Lane for Building and Testing
```ruby
 lane :test_build do
   gradle(task: "testDebugUnitTest")
   gradle(task: "connectedDebugAndroidTest")
   gradle(task: "assembleDebug")
 end
```

### Integration

1.  **Run Fastlane:** Execute the following command from your terminal in the root of your project:
     ```bash
     fastlane test_build
     ```
2.  This command will execute the unit tests, integration tests, and then create a debug build.

## Design

The library uses Material 3 components and styles. This ensures that the buttons follow the latest design guidelines for Android.
The `PermissionSection` component also provides a design element that can group components together nicely.
The `CustomPermissionButton` is very flexible and can be used with any set of permissions.

**Key Design Principles:**

*   **Material 3:**  All UI components adhere to Material Design 3 for a consistent look and feel.
*   **Responsiveness:**  The composables are designed to adapt to different screen sizes and orientations.
*  **Accessibility:**  The UI elements include content descriptions for accessibility.

## Modularity

All components are designed to be modular and reusable. The `CustomPermissionButton` takes a `PermissionType` and handles everything.
The view models are correctly instantiated, ensuring the correct state for each button in the list.

**Key Modular Design:**

*   **Reusability:** Each component is designed to be reusable across different parts of your application.
*   **Flexibility:** The `CustomPermissionButton` handles all permission states, making it easy to use for any permission.
*   **Clear Separation:** The components, contracts, viewmodels, and utils are in their own folders, which helps in the separation of concerns.

## Accessibility

All text components have content descriptions, which is important for accessibility.

## Performance

The library is optimized for performance with minimal computations and lightweight composables. The use of `remember` and `derivedStateOf` avoids unnecessary recompositions.

**Key Performance Optimizations:**

*   **Minimal Recompositions:** The usage of `derivedStateOf` is ideal for preventing unnecessary recompositions.
*  **Lightweight:** Each component is lightweight and only performs necessary operations.

## Documentation

Each button is self-documenting because of its simplicity, but the README provides instructions.
This document provides a comprehensive overview of the library, how to use it, and key design and architectural decisions.
Each public API is properly documented using KDoc.
