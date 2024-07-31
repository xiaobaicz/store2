plugins {
    application
    kotlin("jvm")
}

group = "io.github.xiaobaicz.store2.demo"
version = "0.0.1"

dependencies {
    implementation(project(":store2-saver-mmap"))
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