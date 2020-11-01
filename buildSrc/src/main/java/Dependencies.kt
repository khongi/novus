object Dependencies {
    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object Android {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object AndroidX {
        const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
        const val ktxLifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ktxLifecycle}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val uiTooling = "androidx.ui:ui-tooling:${Versions.compose}"
        }
    }

    object Tests {
        object Framework {
            const val junit = "junit:junit:${Versions.Tests.jUnit}"
        }

        object UI {
            const val androidx_junit = "androidx.test.ext:junit:${Versions.Tests.jUnitExt}"
            const val espresso = "androidx.test.espresso:espresso-core:${Versions.Tests.espresso}"
        }
    }
}