plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.myapplication.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.myapplication.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = jetpack.versions.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":under9-shared-kmm:under9-core-kmm"))
    implementation(jetpack.compose.activity)
    implementation(jetpack.compose.foundation)
    implementation(jetpack.compose.material)
    implementation(jetpack.compose.tooling)
    implementation(jetpack.compose.tooling.preview)
    implementation(jetpack.compose.ui)
}