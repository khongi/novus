plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.Kotlin.android)
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.thiosin.novus"
        minSdkVersion(28)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.Kotlin.stdLib)

    implementation(Dependencies.Android.material)

    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.uiTooling)

    implementation(Dependencies.AndroidX.ktxCore)
    implementation(Dependencies.AndroidX.ktxLifecycle)

    implementation(Dependencies.AndroidX.appCompat)

    testImplementation(Dependencies.Tests.Framework.junit)
    androidTestImplementation(Dependencies.Tests.UI.androidx_junit)
    androidTestImplementation(Dependencies.Tests.UI.espresso)
}