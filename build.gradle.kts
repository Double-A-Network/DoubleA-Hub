plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.6.1" // https://github.com/GradleUp/shadow
}

group = "com.andrew121410.mc"
version = "1.0-SNAPSHOT"
description = "DoubleA-Hub"

java.sourceCompatibility = JavaVersion.VERSION_25
java.targetCompatibility = JavaVersion.VERSION_25

tasks {
    build {
        dependsOn(shadowJar)
    }

    jar {
        enabled = false
    }

    compileJava {
        options.encoding = "UTF-8"
        options.release.set(25)
        options.compilerArgs.add("-Xlint:deprecation")
    }

    shadowJar {
        archiveBaseName.set("DoubleA-Hub")
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }

    maven {
        url = uri("https://repo.opencollab.dev/main/")
    }

    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    compileOnly(libs.io.papermc.paper.paper.api)
    compileOnly(libs.com.github.world1.v6.world1.v6utils.world1.v6utils.plugin)
    compileOnly(libs.com.sk89q.worldguard.worldguard.bukkit)
    compileOnly(libs.org.geysermc.floodgate.api)

    // Jackson -> https://mvnrepository.com/artifact/tools.jackson.core/jackson-core
    compileOnly(libs.jackson.core)
    compileOnly(libs.jackson.databind)
    compileOnly(libs.jackson.dataformat.yaml)
}

val projectVersion = version.toString()
val jacksonVersion = libs.versions.jackson.get()

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", projectVersion)
    inputs.property("jacksonVersion", jacksonVersion)

    filesMatching("plugin.yml") {
        expand("version" to projectVersion, "jacksonVersion" to jacksonVersion)
    }
}