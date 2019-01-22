package org.ninrod.backend

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    val db: String = System.getenv("DATABASE_ADDRESS") ?: "localhost"
    Database.connect(
            "jdbc:postgresql://$db:5432/",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "example"
    )
    transaction {
        addLogger(StdOutSqlLogger)
        for (user in Usuario.selectAll()) {
            println("${user[Usuario.login]}: ${user[Usuario.firstname]}")
        }
    }
}
