package dev.sirtimme.gradle

plugins {
    `maven-publish`
}

fun MavenPom.populate() {
    name = "iuvo"
    description = "Small collection of interfaces to make developing bots with JDA quicker"
    url = "https://github.com/SirTimme/iuvo"
    licenses {
        license {
            name = "The MIT License (MIT)"
            url = "https://mit-license.org/"
        }
    }
    developers {
        developer {
            id = "SirTimme"
            name = "Tim Piechowicz"
            email = "tim@sirtimme.dev"
        }
    }
    scm {
        url = "https://github.com/SirTimme/iuvo"
        connection = "scm:git:git://github.com/SirTimme/iuvo"
        developerConnection = "scm:git:ssh:git@github.com:SirTimme/iuvo"
    }
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
            pom.populate()
        }
        create<MavenPublication>("snapshot") {
            groupId = project.group as String
            artifactId = project.name
            version = "${project.version}-SNAPSHOT"

            from(components["java"])
            pom.populate()
        }
    }
}