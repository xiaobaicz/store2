pluginManagement {
    plugins {
        kotlin("jvm") version "2.0.0" apply false
        kotlin("android") version "2.0.0" apply false
        id("com.android.library") version "8.5.1" apply false
    }
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

rootProject.name = "store2"
include(
    "app",
    "store2",
    "store2-saver-mmap",
    "store2-saver-mmkv",
)