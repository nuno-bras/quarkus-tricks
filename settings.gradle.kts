pluginManagement {
    val quarkusVersion: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id("io.quarkus") version quarkusVersion
    }
}

rootProject.name="quarkus-tricks"
include("hibernate-query-builder", "repository-extension")