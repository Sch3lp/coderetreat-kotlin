plugins {
    kotlin("jvm") version "2.0.0"
}

repositories {
    mavenCentral()
}

allprojects {
    repositories {
        mavenCentral()
    }

    kotlin {
        jvmToolchain(17)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(libs.bundles.coroutines)
        testImplementation(libs.bundles.unittesting)
    }
}