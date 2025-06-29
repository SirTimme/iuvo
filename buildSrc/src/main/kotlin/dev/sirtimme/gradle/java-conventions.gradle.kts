package dev.sirtimme.gradle

plugins {
    java
}

java {
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}