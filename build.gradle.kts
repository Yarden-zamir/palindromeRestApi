import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.6.8"
val logbackVersion = "1.2.5"
val exposedVersion = "0.17.9"
val postgresqlVersion = "42.3.3"
plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
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

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    implementation("org.postgresql:postgresql:$postgresqlVersion")
//    implementation("org.jetbrains.exposed:exposed:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed:0.17.14")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("io.zonky.test:embedded-postgres:1.3.1")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.ktor:ktor-client-core:$ktorVersion")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("ApplicationKt")
}

tasks.create("stage") {
    dependsOn("installDist")
}