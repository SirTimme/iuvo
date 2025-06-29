package dev.sirtimme.gradle

plugins {
    `maven-publish`
}

publishing {
    repositories {
        maven {
            name = "release"
            url = uri("https://patient-turkey-beloved.ngrok-free.app/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "snapshot"
            url = uri("https://patient-turkey-beloved.ngrok-free.app/snapshots")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
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