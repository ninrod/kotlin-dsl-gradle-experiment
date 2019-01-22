import org.gradle.jvm.tasks.Jar

buildscript {
    val artifactory = "http://artifactory/artifactory/gradle"
    fun doWeHaveToUseArtifactory(): Boolean {
        val centos = File("/etc/centos-release").exists()
        val workbench = File("/etc/hostname").readText().trim() == "workbench"
        return centos && workbench
    }
    repositories {
        if (doWeHaveToUseArtifactory()) {
            println("configuring artifactory for plugin repos")
            maven {
                url = uri(artifactory)
            }
        } else {
            println("we are using mavencentral for plugins")
            maven {
                mavenCentral()
                jcenter()
            }
        }
    }
    dependencies {
        if (doWeHaveToUseArtifactory()) {
            println("we are going to add the classpath of the org.jfrog.buildinfo plugin")
            classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.9.0")
        }
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11")
    }
}

plugins {
    java
    application
}
apply(plugin = "kotlin")
apply(plugin = "com.jfrog.artifactory")

application {
    mainClassName = "org.ninrod.backend.EntrypointKt"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


allprojects {
    repositories {
        val artifactory = "http://artifactory/artifactory/gradle"
        fun doWeHaveToUseArtifactory(): Boolean {
            val centos = File("/etc/centos-release").exists()
            val workbench = File("/etc/hostname").readText().trim() == "workbench"
            return centos && workbench
        }
        if (doWeHaveToUseArtifactory()) {
            maven {
                url = uri(artifactory)
            }
        } else {
            mavenCentral()
            jcenter()
        }
    }
}

dependencies {
    // kotlin
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.11")
    compile("org.jetbrains.kotlin:kotlin-reflect:1.3.11")

    // db
    compile("org.jetbrains.exposed:exposed:0.11.2")
    compile("org.jetbrains.exposed:spring-transaction:0.11.2")
    compile("org.postgresql:postgresql:42.2.5")


    // tests
    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.2")
}

tasks {
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        archiveName = "backend-0.0.1.jar"
        from( configurations.runtime.get().map { if (it.isDirectory) it else zipTree(it) })
    }
}
