import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
    id("kotlinx-serialization")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization")
}
version = "1.0"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(shared.bundles.koin)
                api(shared.bundles.ktor)
                api(shared.bundles.stately)
                api(shared.bundles.sqldelight)
                api(shared.eygraber.uri)
                api(shared.kotlin.coroutines.core)
                api(shared.kotlinx.serialization.json)
                api(shared.kotlinx.datetime)
                api(shared.klock)
                api(shared.krypto)
                api(shared.moko.resource.generator)
                api(shared.multiplatform.settings)
                api(shared.napier)
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
                api(jetpack.androidx.lifecycle.viewmodel)
                api(shared.sqldelight.android.driver)
                api(shared.bundles.ktor.android)
                api(shared.kotlin.coroutines.android)
                api(jetpack.androidx.core)
                api(libs.timber)
                api(shared.multiplatform.settings.datastore)
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

            dependencies {
                implementation(shared.sqldelight.native.driver)
                implementation(shared.ktor.client.core)
                implementation(shared.ktor.client.darwin)
                implementation(shared.kotlinx.serialization.json)
            }
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
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            includes.from("README.md")
        }
    }
}

/**
 * Configurations see [https://github.com/icerockdev/moko-resources/]
 */
multiplatformResources {
    multiplatformResourcesPackage = "com.under9.shared.res"
}