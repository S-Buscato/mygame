
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_version: String = "3.4.3"

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
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
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("org.litote.kmongo:kmongo-coroutine:4.2.8")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
}
