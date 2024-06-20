plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(project(":store2"))
    api(libs.gson)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}