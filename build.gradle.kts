import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.6.8"
val logbackVersion = "1.2.5"
plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("com.google.cloud.tools.appengine") version "2.4.2"

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


    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    testImplementation("io.ktor:ktor-client-core:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}

//tasks.withType<KotlinCompile> {
//    kotlinOptions.jvmTarget = "16"
//}

application {
    mainClass.set("ApplicationKt")
}

appengine {
    stage {

//        artifact =
        setArtifact("build/libs/${project.name}-${project.version}-all.jar")
//        artifact =
    }
    deploy {
        version = "GCLOUD_CONFIG"
        projectId = "messagesapi-344913"
        stopPreviousVersion = true
        promote = true
    }
}