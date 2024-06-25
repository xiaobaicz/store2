pluginManagement {
    plugins {
        kotlin("jvm") version "2.0.0"
    }
}

rootProject.name = "store2"
include(
    "app",
    "store2",
    "store2-saver-mmap",
)