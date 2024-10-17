import dev.sirtimme.iuvo.providers.getCommitShort

plugins {
    `java-library`
    `maven-publish`
}

group = "dev.sirtimme"
version = "0.0.2"

repositories {
    mavenCentral()
}

dependencies {
    api("net.dv8tion:JDA:5.0.0") {
        exclude(group = "club.minnced", module = "opus-java")
    }
    api("org.hibernate:hibernate-core:6.4.4.Final")
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "release"
            url = uri("http://192.168.0.227:8082/releases")
            isAllowInsecureProtocol = true
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "snapshot"
            url = uri("http://192.168.0.227:8082/snapshots")
            isAllowInsecureProtocol = true
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
            version = getCommitShort()

            from(components["java"])
        }
    }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    val predicate = provider {
        (repository == publishing.repositories["release"] && publication == publishing.publications["release"])
        ||
        (repository == publishing.repositories["snapshot"] && publication == publishing.publications["snapshot"])
    }
    onlyIf("publishing 'release' to 'release' repository, or 'snapshot' to 'snapshot' repository") {
        predicate.get()
    }
}