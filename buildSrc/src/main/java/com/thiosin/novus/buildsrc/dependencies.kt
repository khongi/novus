package com.thiosin.novus.buildsrc

object Versions {
    const val androidGradlePlugin = "7.0.0-alpha01"

    const val compileSdk = 30
    const val targetSdk = 30
    const val minSdk = 28

    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin = "1.4.20"

    const val rainbowcake = "1.3.0"

    const val compose = "1.0.0-alpha08"
    const val material = "1.2.1"

    const val ktxCore = "1.3.2"
    const val ktxLifecycle = "2.3.0-beta01"
    const val appCompat = "1.2.0"

    const val hilt = "2.29-alpha"
    const val hiltAndroidx = "1.0.0-alpha02"
    const val hiltGradlePlugin = "2.29.1-alpha"

    const val redditARAW = "v1.0.0"
    const val redditAuth = "v1.2.2"

    const val accompanist = "0.4.0"
    const val coil = "1.1.0"

    const val exoplayer = "2.11.8"

    const val okHttp = "4.8.1"
    const val retrofit = "2.9.0"
    const val moshi = "1.10.0"

    const val nav = "2.3.2"

    const val krate = "1.0.0"

    object Tests {
        const val jUnit = "4.13.1"
        const val jUnitExt = "1.1.2"
        const val espresso = "3.3.0"
        const val kotest = "4.3.1"
        const val mockk = "1.10.2"
    }
}

object Dependencies {
    const val kotlin = "1.4.20"
    const val compose = "1.0.0-alpha08"

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
        const val api = "com.github.KirkBushman:ARAW:${Versions.redditARAW}"
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
        const val okHttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitMoshiConverter =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    }

    object Tests {
        object Framework {
            const val junit = "junit:junit:${Versions.Tests.jUnit}"
            const val kotest = "io.kotest:kotest-runner-junit5:${Versions.Tests.kotest}"
            const val rainbowcake = "co.zsmb:rainbow-cake-test:${Versions.rainbowcake}"
        }

        object Assertion {
            const val kotest = "io.kotest:kotest-assertions-core:${Versions.Tests.kotest}"
        }

        object Mock {
            const val mockk = "io.mockk:mockk:${Versions.Tests.mockk}"
        }

        object UI {
            const val androidx_junit = "androidx.test.ext:junit:${Versions.Tests.jUnitExt}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.Tests.espresso}"
        }

        object Hilt {
            const val android = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
        }
    }
}

object Plugins {
    const val androidGradle = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradlePlugin}"
    const val safeArgsGradle = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav}"

    const val androidApplication = "com.android.application"

    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"

    const val hilt = "dagger.hilt.android.plugin"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
}