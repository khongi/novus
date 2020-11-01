plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.0-alpha13")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
}