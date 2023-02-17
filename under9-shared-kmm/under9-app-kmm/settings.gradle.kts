pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Under9AppExample"
rootProject.buildFileName = "build.gradle.kts"
include(":androidApp")
include(":under9-core-kmm")
project(":under9-core-kmm").projectDir = File(settingsDir, "../under9-core-kmm")

include(":under9-chat-kmm:shared")
project(":under9-chat-kmm:shared").projectDir = File(settingsDir, "../under9-chat-kmm/shared")