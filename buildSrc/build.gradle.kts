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
        classpath("gradle.plugin.org.gradle.kotlin:gradle-kotlin-dsl-plugins:1.1.1")
    }
}

apply(plugin = "org.gradle.kotlin.kotlin-dsl")


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
