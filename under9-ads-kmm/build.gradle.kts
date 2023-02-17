import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

version = "1.0"

kotlin {
    android()   
    ios()

    ios {
        binaries {
            framework {
                baseName = "U9Ads"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                // logging
                implementation(project(":under9-core-kmm"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(jetpack.androidx.core)
            }
        }
        val iosMain by getting
    }
}
android {
    compileSdkVersion(shared.versions.compileSdk.get().toInt())

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(shared.versions.minSdk.get().toInt())
        targetSdkVersion(shared.versions.targetSdk.get().toInt())
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}