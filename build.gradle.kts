plugins {
    `java-library`
    `maven-publish`
}

group = "dev.sirtimme"
version = "0.0.1"

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
    publications {
        create<MavenPublication>("release") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            from(components["java"])
        }
    }
}