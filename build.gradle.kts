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
    api("net.dv8tion:JDA:6.4.2") {
        exclude(group = "club.minnced", module = "opus-java")
    }
    api("org.hibernate.orm:hibernate-core:7.4.0.Final")
    api("io.github.classgraph:classgraph:4.8.184")
}

publishing {
    publications {
        register<MavenPublication>("maven") {
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
            name = "Forgejo"
            url = uri("https://forgejo.sirtimme.dev/api/packages/sirtimme/maven")

            credentials(HttpHeaderCredentials::class) {
                name = "Authorization"
                value = "Bearer ${System.getenv("FORGEJO_ACCESS_TOKEN")}"
            }

            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
}
