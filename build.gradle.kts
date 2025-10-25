plugins {
    `java-library`
    `maven-publish`
    id("dev.sirtimme.gradle.java-conventions")
}

group = "dev.sirtimme"
version = "0.0.9"

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
        register<MavenPublication>("release") {
            from(components["java"])

            artifactId = project.name
            groupId = project.group as String
            version = project.version as String

            pom {
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
        }
    }
    repositories {
        maven {
            val releaseUrl = uri("https://artifactory.sirtimme.dev/releases")
            val snapshotUrl = uri("https://artifactory.sirtimme.dev/snapshots")

            name = "artifactory"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotUrl else releaseUrl)

            credentials(PasswordCredentials::class)
        }
    }
}
