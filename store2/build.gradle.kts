plugins {
    `java-library`
    kotlin("jvm")
    `maven-publish`
    signing
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(libs.gson)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.xiaobaicz"
            artifactId = "store2"
            version = "1.2-beta"

            afterEvaluate {
                from(components["java"])
            }

            pom {
                name = "store-mem"
                description = "java abstract store"
                url = "https://github.com/xiaobaicz/store2"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        name = "bocheng.lao"
                        email = "xiaojinjincz@outlook.com"
                        organization = "bocheng.lao"
                        organizationUrl = "https://xiaobaicz.github.io"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/xiaobaicz/store2.git"
                    developerConnection = "scm:git:https://github.com/xiaobaicz/store2.git"
                    url = "https://github.com/xiaobaicz/store2"
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("../build/maven")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["release"])
}