import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.5.20"
    application
}

group = "me.charcoal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))

    //

    implementation("io.ktor:ktor-server-core:1.6.8")
    implementation("io.ktor:ktor-server-netty:1.6.8")
    implementation("ch.qos.logback:logback-classic:1.2.5")
    testImplementation("io.ktor:ktor-server-test-host:1.6.8")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}