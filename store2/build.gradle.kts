plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {
    api(kotlin("reflect"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}