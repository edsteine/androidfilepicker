// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    id("org.jetbrains.dokka") version "1.9.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1" apply false

}