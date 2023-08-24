
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String = "3.4.3"

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "7.1.0"

}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "src/main/kotlin/com/playeranking/Application.kt"
    }
}

group = "com.playeranking"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.cio.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-client-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.litote.kmongo:kmongo-coroutine:4.4.0")
    implementation("org.litote.kmongo:kmongo-serialization:4.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")



    //tests
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.0")
    implementation("io.insert-koin:koin-test:3.4.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.mockk:mockk:1.12.5")
    testImplementation("io.ktor:ktor-client-mock:1.6.1")




    //logs
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("org.slf4j:slf4j-api:1.7.32")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation(kotlin("script-runtime"))
}
