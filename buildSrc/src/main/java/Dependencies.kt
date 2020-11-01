object Dependencies {
    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object Android {
        const val material = "com.google.android.material:material:1.2.1"
    }

    object AndroidX {
        const val ktxCore = "androidx.core:core-ktx:1.3.2"
        const val ktxLifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val uiTooling = "androidx.ui:ui-tooling:${Versions.compose}"
        }
    }

    object Tests {
        object Framework {
            const val junit = "junit:junit:4.13.1"
        }

        object UI {
            const val androidx_junit = "androidx.test.ext:junit:1.1.2"
            const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }
}