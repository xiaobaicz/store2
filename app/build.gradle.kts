plugins {
    application
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

application {
    mainClass.set("io.github.xiaobaicz.store2.demo.MainKt")
}