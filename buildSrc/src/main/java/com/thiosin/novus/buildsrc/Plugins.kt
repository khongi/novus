package com.thiosin.novus.buildsrc

object Plugins {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradlePlugin}"
    const val safeArgsGradle = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"

    const val androidApplication = "com.android.application"
    const val hilt = "dagger.hilt.android.plugin"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"

    object Kotlin {
        const val android = "android"
        const val kapt = "kapt"
    }
}