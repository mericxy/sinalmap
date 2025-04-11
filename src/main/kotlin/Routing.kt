package com.mericxy

import com.mericxy.model.Coordenada
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.text.SimpleDateFormat

fun Application.configureRouting() {
    routing {
        // Rota original (pode ser mantida)
        get("/") {
            call.respondText("Hello World!", ContentType.Text.Plain)
        }

        // Nova rota para retornar coordenadas em JSON
        get("/coordenadas") {
            val coordenadas = mutableListOf<Coordenada>()

            getConnection().use { conn ->
                val statement = conn.createStatement()
                val resultSet = statement.executeQuery("SELECT * FROM coordenadas")

                while (resultSet.next()) {
                    val id = resultSet.getInt("id")
                    val latitude = resultSet.getDouble("latitude")
                    val longitude = resultSet.getDouble("longitude")
                    val intensidade = resultSet.getDouble("intensidade")

                    val timestamp = resultSet.getTimestamp("data_registro")
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val dataFormatada = if (timestamp != null) dateFormat.format(timestamp) else ""

                    coordenadas.add(Coordenada(id, latitude, longitude, intensidade, dataFormatada))
                }
            }

            call.respond(coordenadas)
        }
    }
}