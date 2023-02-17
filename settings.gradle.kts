pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("shared") {
            from(files("under9-shared-kmm/deps/shared.versions.toml"))
        }
        create("jetpack") {
            from(files("under9-shared-kmm/deps/jetpack.versions.toml"))
        }
        create("libs") {
            from(files("under9-shared-kmm/deps/libs.versions.toml"))
        }
    }
}

rootProject.name = "My_Application"
include(":androidApp")
include(":shared")
include(":under9-shared-kmm:under9-core-kmm")