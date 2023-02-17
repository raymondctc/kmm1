buildscript {
    val mokoResourcesGeneratorVersion = shared.versions.mokoResourceGenerator.get()

    dependencies {
        classpath("dev.icerock.moko:resources-generator:$mokoResourcesGeneratorVersion")
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version(shared.versions.pluginGradle.get()).apply(false)
    id("com.android.library").version(shared.versions.pluginGradle.get()).apply(false)
    kotlin("android").version(shared.versions.kotlin.get()).apply(false)
    kotlin("multiplatform").version(shared.versions.kotlin.get()).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
