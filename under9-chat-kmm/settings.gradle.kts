pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "under9-chat-kmm"
include(":androidApp")
project(":androidApp").projectDir = File(settingsDir, "./androidApp")

include(":under9-chat-kmm:shared")
project(":under9-chat-kmm:shared").projectDir = File(settingsDir, "./shared")

include(":under9-common")
project(":under9-common").projectDir = File(settingsDir, "../../under9-common")
include(":under9-common:test-lib")
project(":under9-common:test-lib").projectDir = File(settingsDir, "../../under9-common/test-lib")

include(":under9-base-theme")
project(":under9-base-theme").projectDir = File(settingsDir, "../../under9-base-theme")

include(":under9-widget")
project(":under9-widget").projectDir = File(settingsDir, "../../under9-widget")

include(":under9-arch")
project(":under9-arch").projectDir = File(settingsDir, "../../under9-arch")

include(":under9-tracking")
project(":under9-tracking").projectDir = File(settingsDir, "../../under9-tracking")

include(":under9-network")
project(":under9-network").projectDir = File(settingsDir, "../../under9-network")

include(":under9-media")
project(":under9-media").projectDir = File(settingsDir, "../../under9-media")

include(":under9-core-kmm")
project(":under9-core-kmm").projectDir = File(settingsDir, "../under9-core-kmm")