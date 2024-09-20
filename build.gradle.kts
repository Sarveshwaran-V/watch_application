plugins {
    // Using version catalog (gradle/libs.versions.toml) for dependency versions
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Classpath dependencies for Android, Kotlin, and Google Services plugins
        classpath("com.android.tools.build:gradle:8.6.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.google.gms:google-services:4.3.15")
    }
}

// Remove `allprojects` block if you are using `settings.gradle.kts` to manage repositories.
allprojects {

    }


// Avoid adding repositories here, as repositories are already declared in `settings.gradle.kts`.
