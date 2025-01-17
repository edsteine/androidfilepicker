plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jetbrains.dokka")

}

android {
    namespace = "com.edsteine.androidfilepicker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.edsteine.androidfilepicker"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }


    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("Boolean", "ENABLE_TIMBER", "false")
            isDebuggable = false
        }
        debug {
            isMinifyEnabled = false
            buildConfigField("Boolean", "ENABLE_TIMBER", "true")
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }


    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//            excludes += "lib/**/*.so"
        }
    }


    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}


kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    // Core Android KTX - Provides Kotlin extensions for Android core functionality
    // Used for core Android features like coroutines integration, collections extensions
    implementation(libs.androidx.core.ktx)

    // Lifecycle Runtime KTX - Handles lifecycle-aware components with Kotlin coroutines
    // Used for ViewModels, lifecycleScope, and other lifecycle management
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity Compose - Integrates Compose with Android Activities
    // Required for setting up Compose in Activities and basic navigation
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose) // Navigation for Compose

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Compose BOM - Bill of Materials for version coordination
    // Ensures all Compose dependencies use compatible versions
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI - Core UI toolkit for building Compose applications
    // Provides basic composable, layout system, and UI primitives
    implementation(libs.androidx.ui)

    // Compose Graphics - Graphics engine and drawing APIs for Compose
    // Used for custom drawing, animations, and graphics operations
    implementation(libs.androidx.ui.graphics)

    // Compose Tooling Preview - Enables @Preview functionality in Android Studio
    // Used for previewing composable during development
    implementation(libs.androidx.ui.tooling.preview)


    // Material3 - Implementation of Material Design 3 for Compose
    // Provides Material Design components, theming, and styling
    implementation(libs.material3)

    // Material3 Icons Extended - Comprehensive set of Material Design icons
    // Provides access to the full set of Material Design icons
    implementation(libs.material3.icons.extended)

    // Compose Runtime - Core runtime for Compose state management
    // Handles state management, composition, and recomposition
    implementation(libs.androidx.runtime)

    // Accompanist Permissions - Simplifies runtime permissions in Compose
    // Used for handling Android runtime permissions with Compose UI
    implementation(libs.accompanist.permissions)

    // JUnit Jupiter BOM - Bill of Materials for JUnit 5
    // Coordinates versions for all JUnit 5 testing components
    testImplementation(platform(libs.junit.bom))

    // JUnit Jupiter API - Testing annotations and assertions
    // Provides the API for writing JUnit 5 tests
    testImplementation(libs.junit.jupiter.api)
    androidTestImplementation(libs.junit.jupiter)


    // JUnit Jupiter Engine - Test execution engine
    // Required for running JUnit 5 tests
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)



    // AndroidX Test Extensions - JUnit extensions for Android
    // Provides Android-specific testing utilities
    androidTestImplementation(libs.androidx.test.ext)

    // AndroidX Test Runner - Runs Android tests
    // Required for executing instrumented tests
    androidTestImplementation(libs.androidx.test.runner)

    // Compose UI Testing - Testing utilities for Compose
    // Enables testing of Compose UI components
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.compose.ui.test.junit4)

    // Compose UI Tooling - Development tools for Compose
    // Provides debugging tools and preview functionality
    debugImplementation(libs.androidx.ui.tooling)

    // Compose Test Manifest - Test manifest for UI tests
    // Required for running Compose UI tests
    debugImplementation(libs.androidx.ui.test.manifest)


    // Logging Library
    implementation(libs.timber) // A logger with a small, extensible API


    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.manifest)



    testImplementation(libs.mockk) // Mocking library for Kotlin

    testImplementation(libs.kotlinx.coroutines.test)
}


detekt {
    buildUponDefaultConfig = true
    parallel = true
    buildUponDefaultConfig = true
    allRules = false
}


ktlint {
    // Configure ktlint
    version.set("0.48.2")
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    enableExperimentalRules.set(true)
//    additionalRuleSets.set(listOf())
}

// In build.gradle or build.gradle.kts
configurations.all {
    resolutionStrategy {
        failOnVersionConflict()
        preferProjectModules()
    }
    if (name == "debugRuntimeClasspathCopy") {
        isCanBeConsumed = false
        isCanBeResolved = false
    }

}
configurations.configureEach {
    if (name == "debugRuntimeClasspathCopy") {
        isCanBeConsumed = false
        isCanBeResolved = false
    }
}