buildscript {
    val kotlin_version = shared.versions.kotlin.get()
    val sqldelight_version = shared.versions.sqlDelight.get()
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:4.2.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath ("com.squareup.sqldelight:gradle-plugin:$sqldelight_version")
    }
}

allprojects {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
        maven {
            url = uri("https://jitpack.io")
        }
        maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
        maven { url = uri("https://jetbrains.bintray.com/intellij-third-party-dependencies") }

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}