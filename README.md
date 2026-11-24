# Android Permissions Demo

A native Android sample and reusable Jetpack Compose component set for checking
runtime permission state, requesting permissions, showing rationale, and opening
app settings after permanent denial.

Despite the repository name, this project is a permission-management demo. It
does not provide a general-purpose document or file picker.

The repository is currently intended for GitHub and portfolio use. It is not
configured as a Play Store product: it has no signing key, privacy policy flow,
store assets, or production backend.

## Requirements

- Android Studio with JDK 17
- Android SDK 36
- Android device or emulator on API 26 or newer

## Build and test

```sh
./gradlew lint
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew bundleRelease
```

Run `./gradlew connectedDebugAndroidTest` with an attached emulator for the
Compose instrumentation test.

## Output and scope

- `assembleDebug` creates a locally installable debug APK.
- `bundleRelease` creates an unsigned, locally verifiable release bundle.
- Build output under `app/build/` is generated and ignored by Git.

The manifest intentionally contains many permissions so the sample can
demonstrate permission states. Review and reduce them before turning this into
a consumer app or submitting it to Google Play.

## Verification

The maintained local verification commands are:

```sh
./gradlew testDebugUnitTest
./gradlew lint
./gradlew bundleRelease
git diff --check
```

An attached emulator is required for `connectedDebugAndroidTest`.
