buildscript {
    val kotlin_version = shared.versions.kotlin.get()

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("com.squareup.sqldelight:gradle-plugin:${shared.versions.sqlDelight.get()}")
        classpath("com.android.tools.build:gradle:${shared.versions.pluginGradle.get()}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}