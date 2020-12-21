@file:Suppress("SpellCheckingInspection", "unused")

package com.thiosin.novus.buildsrc

object Versions {
    const val androidGradlePlugin = "7.0.0-alpha03"

    const val compileSdk = 30
    const val targetSdk = 30
    const val minSdk = 28

    const val versionCode = 1
    const val versionName = "1.0"

    const val accompanist = "0.4.1"
    const val appCompat = "1.2.0"
    const val coil = "1.1.0"
    const val compose = "1.0.0-alpha09"
    const val coroutine = "1.4.2"
    const val detekt = "1.14.2"
    const val exoplayer = "2.12.2"
    const val gradleVersions = "0.36.0"
    const val hilt = "2.29-alpha"
    const val hiltAndroidx = "1.0.0-alpha02"
    const val hiltGradlePlugin = "2.29.1-alpha"
    const val jacoco = "0.8.6"
    const val kotlin = "1.4.21"
    const val krate = "1.0.0"
    const val ktlint = "9.4.1"
    const val ktxCore = "1.3.2"
    const val ktxLifecycle = "2.3.0-beta01"
    const val material = "1.2.1"
    const val moshi = "1.11.0"
    const val nav = "2.3.2"
    const val okHttp = "4.8.1"
    const val rainbowcake = "1.3.0"
    const val redditAuth = "1.2.2"
    const val retrofit = "2.9.0"

    object Tests {
        const val androidX = "1.2.0"
        const val espresso = "3.3.0"
        const val jUnit = "4.13.1"
        const val jUnitExt = "1.1.2"
        const val kotest = "4.3.2"
        const val mockk = "1.10.3"
    }
}

object Dependencies {

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object RainbowCake {
        const val core = "co.zsmb:rainbow-cake-core:${Versions.rainbowcake}"
        const val navigation = "co.zsmb:rainbow-cake-navigation:${Versions.rainbowcake}"
        const val timber = "co.zsmb:rainbow-cake-timber:${Versions.rainbowcake}"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    }

    object Android {
        const val material = "com.google.android.material:material:${Versions.material}"
        const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}"
    }

    object AndroidX {
        const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
        const val ktxLifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxLifecycle}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

        object Hilt {
            const val viewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltAndroidx}"
            const val compiler = "androidx.hilt:hilt-compiler:${Versions.hiltAndroidx}"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:${Versions.compose}"
        }

        object Navigation {
            const val ktxFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
            const val ktxUi = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
        }
    }

    object Reddit {
        const val auth = "com.github.KirkBushman:Android-Reddit-OAuth2:${Versions.redditAuth}"
    }

    object Accompanist {
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:${Versions.accompanist}"
    }

    object Coil {
        const val gif = "io.coil-kt:coil-gif:${Versions.coil}"
    }

    object Storage {
        const val krate = "hu.autsoft:krate:${Versions.krate}"
        const val krateMoshiCodegen = "hu.autsoft:krate-moshi-codegen:${Versions.krate}"
    }

    object Network {
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Tests {
        object Framework {
            const val junit = "junit:junit:${Versions.Tests.jUnit}"
            const val kotest = "io.kotest:kotest-runner-junit5:${Versions.Tests.kotest}"
            const val rainbowcake = "co.zsmb:rainbow-cake-test:${Versions.rainbowcake}"
            const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"
        }

        object Assertion {
            const val kotest = "io.kotest:kotest-assertions-core:${Versions.Tests.kotest}"
        }

        object Mock {
            const val mockk = "io.mockk:mockk:${Versions.Tests.mockk}"
        }

        object UI {
            const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
            const val core = "androidx.test:core:${Versions.Tests.androidX}"
            const val rules = "androidx.test:rules:${Versions.Tests.androidX}"
            const val ktxJUnit = "androidx.test.ext:junit-ktx:${Versions.Tests.jUnitExt}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.Tests.espresso}"
        }

        object Hilt {
            const val android = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
        }
    }
}

object Plugins {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val detektGradle = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradlePlugin}"
    const val jacocoGradle = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val ktlintGradle = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    const val safeArgsGradle = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"
    const val versionGradle = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}"
}
