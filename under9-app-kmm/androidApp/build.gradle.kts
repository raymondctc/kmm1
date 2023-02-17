plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":ninegag-shared-lib"))
    implementation(jetpack.androidx.material)
    implementation(jetpack.androidx.appcompat)
    implementation(jetpack.androidx.constraintlayout)
    implementation(jetpack.bundles.compose.common)
    implementation(jetpack.compose.viewmodel)
    implementation(jetpack.compose.viewmodel)
    implementation(jetpack.compose.constraintlayout)
    implementation(jetpack.compose.navigation)
}

android {
    compileSdk = shared.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "com.under9.android.app"
        minSdk = shared.versions.minSdk.get().toInt()
        targetSdk = shared.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = jetpack.versions.composeCompiler.get()
    }
}