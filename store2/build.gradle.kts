plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(kotlin("reflect"))
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