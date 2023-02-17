import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization")
}

version = "1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

//    ios {
//        compilations {
//            val main by getting {
//                kotlinOptions {
//                    freeCompilerArgs = listOf(
//                        *kotlinOptions.freeCompilerArgs.toTypedArray(),
//                        "-Xopt-in=kotlin.time.ExperimentalTime"
//                    )
//                }
//            }
//        }
//        binaries {
//            framework {
//                baseName = "U9Chat"
//            }
//        }
//    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":under9-analytics-kmm"))
                implementation(project(":under9-core-kmm"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(jetpack.androidx.core)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting {}
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = shared.versions.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = shared.versions.minSdk.get().toInt()
        targetSdk = shared.versions.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    // https://stackoverflow.com/questions/55456176/unresolved-reference-compilekotlin-in-build-gradle-kts
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs =
                listOf(
                    *kotlinOptions.freeCompilerArgs.toTypedArray(),
                    "-Xopt-in=kotlin.time.ExperimentalTime"
                )
        }
    }

}

sqldelight {
    database("ChatDatabase") {
        packageName = "com.under9.shared.chat.db"
//        schemaOutputDirectory = file("src/commonMain/com/under9/shared/chat/db")
//        schemaOutputDirectory = file("src/commonMain/sqldelight/com/under9/shared/chat/db")
//        sourceFolders = listOf("db")
    }
}