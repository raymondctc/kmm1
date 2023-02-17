pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

// Wrapper library modules
include(":ninegag-shared-lib")
include(":ninegag-shared-app")
include(":ninegag-shared-app-test")

// Library modules
include(":under9-analytics-kmm")
include(":under9-core-kmm")

// Example app
include(":under9-app-kmm")
include(":under9-app-kmm:androidApp")

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("shared") {
            from(files("./deps/shared.versions.toml"))
        }
        create("jetpack") {
            from(files("./deps/jetpack.versions.toml"))
        }
        create("libs") {
            from(files("./deps/libs.versions.toml"))
        }
    }
}
