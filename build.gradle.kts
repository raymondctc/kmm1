buildscript {
    val kotlinVersion = shared.versions.kotlin.get()
    val mokoResourcesGeneratorVersion = shared.versions.mokoResourceGenerator.get()
    val sqlDelightVersion = shared.versions.sqlDelight.get()

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(shared.plugin.gradle)
        classpath ("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
        classpath("dev.icerock.moko:resources-generator:$mokoResourcesGeneratorVersion")
        classpath(shared.plugin.dokka)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

apply(plugin = "org.jetbrains.dokka")

tasks.withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    afterEvaluate {
        if (tasks.findByName("dokkaHtmlPartial") == null) {
            // If dokka isn't enabled on this module, skip
        }
    }
}

// FIXME To be addressed later
//
// Not sure why after adding js {} block in subproject, it will throw
// Caused by: org.gradle.api.internal.tasks.DefaultTaskContainer$DuplicateTaskException: Cannot add task 'clean' as a task with that name already exists.
// During gradle sync
// Issue: https://youtrack.jetbrains.com/issue/KT-33191
//tasks.register("clean", Delete::class) {
//    delete(rootProject.buildDir)
//}