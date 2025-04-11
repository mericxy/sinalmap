package com.mericxy

import io.ktor.server.application.*
import java.sql.Connection
import java.sql.DriverManager

fun Application.getConnection(): Connection {
    val jdbcUrl = environment.config.property("database.jdbcUrl").getString()
    val username = environment.config.property("database.username").getString()
    val password = environment.config.property("database.password").getString()

    // Carregar o driver do PostgreSQL
    Class.forName("org.postgresql.Driver")

    // Retornar conexão
    return DriverManager.getConnection(jdbcUrl, username, password)
}