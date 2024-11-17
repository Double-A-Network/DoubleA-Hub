plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.5" // https://github.com/GradleUp/shadow
}

group = "com.andrew121410.mc"
version = "1.0-SNAPSHOT"
description = "DoubleA-Hub"

java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

tasks {
    build {
        dependsOn("shadowJar")
        dependsOn("processResources")
    }

    jar {
        enabled = false
    }

    compileJava {
        options.encoding = "UTF-8"
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

    // Jackson -> https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    val jacksonVersion = libs.versions.jackson.get()
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    compileOnly("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    compileOnly("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    compileOnly("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
}

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", project.version)
    inputs.property("jacksonVersion", libs.versions.jackson.get())

    filesMatching("plugin.yml") {
        expand("version" to project.version, "jacksonVersion" to libs.versions.jackson.get())
    }
}