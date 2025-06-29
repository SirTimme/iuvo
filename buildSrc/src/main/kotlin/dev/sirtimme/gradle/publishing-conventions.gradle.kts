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
}