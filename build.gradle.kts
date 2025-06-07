import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "2.1.20"
}

group = "me.syncwrld.minecraft.pingcompass"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven(uri("https://repo.extendedclip.com/content/repositories/placeholderapi/"))
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("com.github.cryptomorin:XSeries:13.3.1")
    implementation("org.bstats:bstats-bukkit:3.0.2")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
}

tasks.build {
    dependsOn(tasks.withType<Copy>())
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    relocate("com.cryptomorin.xseries", "me.syncwrld.minecraft.pingcompass.libs.xseries")
    relocate("org.bstats", "me.syncwrld.minecraft.pingcompass.libs.bstats")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val copyJar by tasks.registering(Copy::class) {
    dependsOn(tasks.withType<ShadowJar>())
    from(tasks.shadowJar.get().archiveFile)
    into("/artifacts/")
    rename { "PingCompass.jar" }
}

kotlin {
    jvmToolchain(17)
}