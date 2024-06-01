rootProject.name = "coderetreat-kotlin"

val junitVersion = "5.10.2"
val kotlinxCoroutinesVersion = "1.8.1"
val assertjVersion = "3.26.0"
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("kotlinx-coroutines-bom", "org.jetbrains.kotlinx:kotlinx-coroutines-bom:$kotlinxCoroutinesVersion")
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")
            library("kotlinx-coroutines-reactor", "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinxCoroutinesVersion")
            bundle("coroutines", listOf(
                "kotlinx-coroutines-bom",
                "kotlinx-coroutines-core",
                "kotlinx-coroutines-reactor",
            ))

            library("junit-jupiter", "org.junit.jupiter:junit-jupiter:$junitVersion")
            library("junit-params", "org.junit.jupiter:junit-jupiter-params:$junitVersion")
            library("assertj", "org.assertj:assertj-core:$assertjVersion")
            library("kotlinx-coroutines-test", "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion")
            bundle("unittesting", listOf(
                    "junit-jupiter",
                    "junit-params",
                    "assertj",
                    "assertj",
            ))


        }
    }
}