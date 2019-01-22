import org.gradle.jvm.tasks.Jar
import org.ninrod.backend.build.*

buildscript {
    val artifactory = "http://artifactory/artifactory/gradle"
    repositories {
        // BUG HERE: I have to use the full qualified name of the doWeHaveToUseArtifactory
        // because the buildScript block does not respect the top level defined imports!
        if (org.ninrod.backend.build.doWeHaveToUseArtifactory()) {
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
        // BUG HERE: I have to use the full qualified name of the doWeHaveToUseArtifactory
        // because the buildScript block does not respect the top level defined imports!
        if (org.ninrod.backend.build.doWeHaveToUseArtifactory()) {
            println("we are going to add the classpath of the org.jfrog.buildinfo plugin")
            classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.9.0")
        }
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.11")
    }
}

apply(plugin = "kotlin")
apply(plugin = "com.jfrog.artifactory")
plugins {
    application
}

application {
    mainClassName = "org.ninrod.backend.EntrypointKt"
    version = "0.0.1"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    val artifactory = "http://artifactory/artifactory/gradle"
    if (doWeHaveToUseArtifactory()) {
        maven {
            url = uri(artifactory)
        }
    } else {
        mavenCentral()
        jcenter()
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
    "jar"(Jar::class) {
        baseName = project.name
        manifest {
            attributes["Main-Class"] = application.mainClassName
        }
        from( configurations.runtime.get().map { if (it.isDirectory) it else zipTree(it) })
    }

    val dump by creating {
        println("CONFIGURATION PHASE!!!")
        println(hello())
        println("temos que usar artifactory? " + doWeHaveToUseArtifactory())
        doLast {
            println("EXECUTION PHASE!!!")
        }
    }

    val compute by creating {
        doLast {
            val label: String by project
            val answer: String by project
            println("The $label = $answer.")

            val artifactory_contextUrl: String by project
            val artifactory_gradle: String by project
            val kotlin_version: String by project
            val exposed_version: String by project
            val junit5_version: String by project
            val postgresql_driver_version: String by project
            val springboot_version: String by project

            println("$artifactory_contextUrl")
            println("$artifactory_gradle")
            println("$kotlin_version")
            println("$exposed_version")
            println("$junit5_version")
            println("$postgresql_driver_version")
            println("$springboot_version")
        }
    }
}
