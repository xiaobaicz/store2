plugins {
    kotlin("android")
    id("com.android.library")
    `maven-publish`
    signing
}

android {
    namespace = "io.github.xiaobaicz.store2.saver"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.core.ktx)
    api(project(":store2"))
    api(libs.mmkv)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.xiaobaicz"
            artifactId = "store2-saver-mmkv"
            version = "1.3.1"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name = "store-mmkv"
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