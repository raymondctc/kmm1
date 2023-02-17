
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization")
}

version = "1.0"

object BuildConfigKey {
    const val ENV = "ENV"
    const val ENDPOINT_API = "ENDPOINT_API"
    const val ENDPOINT_NOTI_API = "ENDPOINT_NOTI_API"
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(shared.moko.resource.generator)
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
                implementation(libs.cronet.okhttp)
                implementation(libs.cronet.playServices)
                implementation(jetpack.androidx.core)
                implementation(jetpack.androidx.preference)
                implementation(shared.sqldelight.android.driver)
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
            dependencies {
                implementation(shared.crashlyticskios)
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
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
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")

        // FIXME: https://github.com/icerockdev/moko-resources/issues/353#issuecomment-1179713713
        res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    }
    
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

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            includes.from("README.md")
        }
    }
}

sqldelight {
    database("SharedNineGagDatabase") {
        packageName = "com.ninegag.app.shared.db"
//        schemaOutputDirectory = file("src/commonMain/com/under9/shared/chat/db")
//        schemaOutputDirectory = file("src/commonMain/sqldelight/com/under9/shared/chat/db")
//        sourceFolders = listOf("db")
    }
}

/**
 * Configurations see [https://github.com/icerockdev/moko-resources/]
 */
multiplatformResources {
    multiplatformResourcesPackage = "com.ninegag.app.shared.res"
}