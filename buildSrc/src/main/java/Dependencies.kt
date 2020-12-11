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