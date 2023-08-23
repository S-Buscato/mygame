package com.playeranking.plugins

import com.playeranking.routes.playersRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/api") {
            playersRoutes()
        }
    }
}
