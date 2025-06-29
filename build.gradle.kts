plugins {
    `java-library`
    id("dev.sirtimme.gradle.java-conventions")
    id("dev.sirtimme.gradle.publishing-conventions")
}

group = "dev.sirtimme"
version = "0.0.8"

repositories {
    mavenCentral()
}

dependencies {
    api("net.dv8tion:JDA:5.6.1") {
        exclude(group = "club.minnced", module = "opus-java")
    }
    api("org.hibernate:hibernate-core:7.0.3.Final")
    api("io.github.classgraph:classgraph:4.8.180")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            from(components["java"])
        }
        create<MavenPublication>("snapshot") {
            groupId = project.group as String
            artifactId = project.name
            version = "${project.version}-SNAPSHOT"

            from(components["java"])
        }
    }
}