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
            const val uiTooling = "androidx.ui:ui-tooling:${Versions.compose}"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
            const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
        }
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