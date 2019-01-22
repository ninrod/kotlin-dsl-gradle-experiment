package org.ninrod.backend.build

import java.io.File

fun hello(): String = "hello, world!"

fun doWeHaveToUseArtifactory(): Boolean {
    val centos = File("/etc/centos-release").exists()
    val workbench = File("/etc/hostname").readText().trim() == "workbench"
    return centos && workbench
}
