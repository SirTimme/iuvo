plugins {
    `java-library`
    `maven-publish`
}

group = "dev.sirtimme"
version = "0.0.7"

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

java {
    withSourcesJar()
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
            version = getCommitShort()

            from(components["java"])
        }
    }
}

fun PublishToMavenRepository.isRepository(repositoryName: String): Boolean {
    return repository == publishing.repositories[repositoryName]
}

fun PublishToMavenRepository.isPublication(publicationName: String): Boolean {
    return publication == publishing.publications[publicationName]
}

tasks.withType<PublishToMavenRepository>().configureEach {
    onlyIf("publish 'release' to 'release' repository or 'snapshot' to 'snapshot' repository") {
        (isRepository("release") && isPublication("release")) || (isRepository("snapshot") && isPublication("snapshot"))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}