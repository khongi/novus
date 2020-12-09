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
        buildConfigField("String","REDIRECT_URL","\"novus-app://auth/reddit-redirect\"")
        buildConfigField("String","BASE_URL","\"https://oauth.reddit.com\"")
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
//
//    val nav_version = "2.3.2"
//    val nav_compose_version = "1.0.0-alpha03"
//    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
//    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
//    implementation("androidx.navigation:navigation-compose:$nav_compose_version")

    implementation(Dependencies.AndroidX.ktxCore)
    implementation(Dependencies.AndroidX.ktxLifecycle)

    implementation(Dependencies.AndroidX.appCompat)

    implementation(Dependencies.Reddit.auth)
    implementation(Dependencies.Reddit.api)

    implementation(Dependencies.Accompanist.coil)
    implementation(Dependencies.Coil.gif)
    implementation(Dependencies.Android.exoplayer)

    implementation(Dependencies.Network.okHttp)
    implementation(Dependencies.Network.okHttpLoggingInterceptor)
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.retrofitMoshiConverter)
    implementation(Dependencies.Network.moshi)
    kapt(Dependencies.Network.moshiCodegen)

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