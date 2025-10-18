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
            url = uri("https://artifactory.sirtimme.dev/releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "snapshot"
            url = uri("https://artifactory.sirtimme.dev/snapshots")
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
