import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources") //Ref: https://github.com/icerockdev/moko-resources/issues/173
    id("kotlin-parcelize")
    kotlin("multiplatform")
//    kotlin("native.cocoapods")
}

version = "1.0"

kotlin {
    android()

    val frameworkName = "NineGagKmm"
    val xcf = XCFramework(frameworkName)
    val ios64 = iosX64()
    val iosArm64 = iosArm64()
    val iosSimulatorArm64 = iosSimulatorArm64()

    configure(listOf(ios64, iosArm64, iosSimulatorArm64)) {
        binaries.framework {
            export(project(":ninegag-shared-app"))
            export(project(":under9-analytics-kmm"))
            export(project(":under9-core-kmm"))
            export(shared.moko.resource.generator) // export correct artifact to use all classes of moko-resources directly from Swift
            xcf.add(this)
            linkerOpts.add("-lsqlite3")
            baseName = frameworkName
        }
    }

    /* Disabled cocapods in favor of XCFramework
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    targets.withType<KotlinNativeTarget> { // This one to access the result iOS target.
        binaries.withType<org.jetbrains.kotlin.gradle.plugin.mpp.Framework> { // This one to access the framework created by the cocoapods plugin.
            export(project(":under9-analytics-kmm"))
            export(project(":under9-core-kmm"))
            export(project(":under9-chat-kmm:shared"))
            export(project(":ninegag-shared-analytics"))
            export(shared.moko.resource.generator) // export correct artifact to use all classes of moko-resources directly from Swift
            export(project(":ninegag-shared-app"))
        }
    }
    
    cocoapods {
        ios.deploymentTarget = "11.0"
        summary = "Core common library"
        homepage = "Link to the Shared Module homepage"
        framework {
            baseName = "ninegag_shared_lib"
        }
        noPodspec()

        // Maps custom Xcode configuration to NativeBuildType
        xcodeConfigurationToNativeBuildType["Debug"] = NativeBuildType.DEBUG
        xcodeConfigurationToNativeBuildType["Release"] = NativeBuildType.RELEASE
        xcodeConfigurationToNativeBuildType["Production"] = NativeBuildType.RELEASE
    }
     */

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":under9-analytics-kmm"))
                api(project(":under9-core-kmm"))
                api(project(":ninegag-shared-app"))
                api(shared.moko.resource.generator)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":ninegag-shared-app-test"))
                implementation(shared.koin.test)
                implementation(shared.kotlin.coroutines.test)
                implementation(shared.ktor.client.mock)
            }
        }
        val androidMain by getting
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
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = shared.versions.minSdk.get().toInt()
        targetSdk = shared.versions.targetSdk.get().toInt()
    }
    // https://stackoverflow.com/questions/55456176/unresolved-reference-compilekotlin-in-build-gradle-kts
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

/**
 * Configurations see [https://github.com/icerockdev/moko-resources/]
 */
multiplatformResources {
    multiplatformResourcesPackage = "com.ninegag.shared.res"
}
