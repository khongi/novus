plugins {
    id(com.thiosin.novus.buildsrc.Plugins.androidApplication)
    id(com.thiosin.novus.buildsrc.Plugins.hilt)
    id(com.thiosin.novus.buildsrc.Plugins.kotlinAndroid)
    id(com.thiosin.novus.buildsrc.Plugins.kotlinKapt)
    id(com.thiosin.novus.buildsrc.Plugins.kotlinAndroidExtensions)
    id(com.thiosin.novus.buildsrc.Plugins.safeArgs)
}

android {
    compileSdkVersion(com.thiosin.novus.buildsrc.Versions.compileSdk)

    defaultConfig {
        applicationId = "com.thiosin.novus"
        minSdkVersion(com.thiosin.novus.buildsrc.Versions.minSdk)
        targetSdkVersion(com.thiosin.novus.buildsrc.Versions.targetSdk)
        versionCode = com.thiosin.novus.buildsrc.Versions.versionCode
        versionName = com.thiosin.novus.buildsrc.Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "CLIENT_ID", "\"04WMjFYDMJFljQ\"")
        buildConfigField("String", "REDIRECT_URL", "\"novus-app://auth/reddit-redirect\"")
        buildConfigField("String", "BASE_URL", "\"https://oauth.reddit.com\"")
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

    buildFeatures {
        viewBinding = true
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
        kotlinCompilerExtensionVersion = com.thiosin.novus.buildsrc.Versions.compose
        kotlinCompilerVersion = com.thiosin.novus.buildsrc.Versions.kotlin
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
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(com.thiosin.novus.buildsrc.Dependencies.Kotlin.stdLib)

    implementation(com.thiosin.novus.buildsrc.Dependencies.RainbowCake.core)
    implementation(com.thiosin.novus.buildsrc.Dependencies.RainbowCake.timber)

    implementation(com.thiosin.novus.buildsrc.Dependencies.Hilt.android)
    kapt(com.thiosin.novus.buildsrc.Dependencies.Hilt.compiler)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Hilt.viewModel)
    kapt(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Hilt.compiler)

    implementation(com.thiosin.novus.buildsrc.Dependencies.Android.material)

    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Compose.ui)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Compose.material)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Compose.uiTooling)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Compose.livedata)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Compose.viewBinding)

    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Navigation.ktxFragment)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.Navigation.ktxUi)

    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.ktxCore)
    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.ktxLifecycle)

    implementation(com.thiosin.novus.buildsrc.Dependencies.AndroidX.appCompat)

    implementation(com.thiosin.novus.buildsrc.Dependencies.Reddit.auth)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Reddit.api)

    implementation(com.thiosin.novus.buildsrc.Dependencies.Accompanist.coil)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Coil.gif)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Android.exoplayer)

    implementation(com.thiosin.novus.buildsrc.Dependencies.Network.okHttp)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Network.okHttpLoggingInterceptor)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Network.retrofit)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Network.retrofitMoshiConverter)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Network.moshi)
    kapt(com.thiosin.novus.buildsrc.Dependencies.Network.moshiCodegen)

    implementation(com.thiosin.novus.buildsrc.Dependencies.Storage.krate)
    implementation(com.thiosin.novus.buildsrc.Dependencies.Storage.krateMoshiCodegen)

    testImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Framework.kotest)
    testImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Assertion.kotest)
    testImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Framework.rainbowcake)
    testImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Mock.mockk)
    testImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Hilt.android)
    kaptTest(com.thiosin.novus.buildsrc.Dependencies.Hilt.compiler)

    androidTestImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Framework.junit)
    androidTestImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.UI.androidx_junit)
    androidTestImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.UI.espresso)
    androidTestImplementation(com.thiosin.novus.buildsrc.Dependencies.Tests.Hilt.android)
    kaptAndroidTest(com.thiosin.novus.buildsrc.Dependencies.Hilt.compiler)
}