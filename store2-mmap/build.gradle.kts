plugins {
    `java-library`
    kotlin("jvm")
}

dependencies {

}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}