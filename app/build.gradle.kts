plugins {
    id(Plugins.androidApplication)
    id(Plugins.hilt)
    kotlin(Plugins.Kotlin.android)
    kotlin(Plugins.Kotlin.kapt)
}

android {
    compileSdkVersion(Versions.compileSdk)

    defaultConfig {
        applicationId = "com.thiosin.novus"
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","CLIENT_ID","\"04WMjFYDMJFljQ\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        useIR = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
        kotlinCompilerVersion = Versions.kotlin
    }

    lintOptions {
        isAbortOnError = false
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-progressive"
        freeCompilerArgs = freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.Kotlin.stdLib)

    implementation(Dependencies.RainbowCake.core)
    implementation(Dependencies.RainbowCake.navigation)
    implementation(Dependencies.RainbowCake.timber)

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)
    implementation(Dependencies.AndroidX.Hilt.viewModel)
    kapt(Dependencies.AndroidX.Hilt.compiler)

    implementation(Dependencies.Android.material)

    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.uiTooling)
    implementation(Dependencies.AndroidX.Compose.runtimeLivedata)
    implementation(Dependencies.AndroidX.Compose.paging)

    implementation(Dependencies.AndroidX.ktxCore)
    implementation(Dependencies.AndroidX.ktxLifecycle)

    implementation(Dependencies.AndroidX.appCompat)

    implementation(Dependencies.Reddit.auth)

    testImplementation(Dependencies.Tests.Framework.kotest)
    testImplementation(Dependencies.Tests.Assertion.kotest)
    testImplementation(Dependencies.Tests.Framework.rainbowcake)
    testImplementation(Dependencies.Tests.Mock.mockk)
    testImplementation(Dependencies.Tests.Hilt.android)
    kaptTest(Dependencies.Hilt.compiler)

    androidTestImplementation(Dependencies.Tests.Framework.junit)
    androidTestImplementation(Dependencies.Tests.UI.androidx_junit)
    androidTestImplementation(Dependencies.Tests.UI.espresso)
    androidTestImplementation(Dependencies.Tests.Hilt.android)
    kaptAndroidTest(Dependencies.Hilt.compiler)
}