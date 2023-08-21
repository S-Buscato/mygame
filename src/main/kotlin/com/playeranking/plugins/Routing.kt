package com.playeranking.plugins

import com.playeranking.routes.playersRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    install(ContentNegotiation) {
        json(Json {
            // Configurez ici les paramètres de sérialisation si nécessaire
            prettyPrint = true // Par exemple, pour une mise en forme lisible
        })
    }

    routing {
        playersRoutes()
    }
}
