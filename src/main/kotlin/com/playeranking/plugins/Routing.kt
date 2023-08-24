package com.playeranking.plugins

import com.playeranking.exception.PlayerRouteException
import com.playeranking.routes.playersRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    install(StatusPages) {exception<Throwable> { call, cause ->
            if(cause is PlayerRouteException.PlayerAlreadyExists) {
                call.respondText(text = "403: $cause" , status = HttpStatusCode.NotModified)
            } else if(cause is PlayerRouteException.InvalidScore) {
                call.respondText(text = "403: $cause" , status = HttpStatusCode.NotModified)
            } else if(cause is PlayerRouteException.PlayerDoesNotExist) {
                call.respondText(text = "403: $cause" , status = HttpStatusCode.NotFound)
            }
        }
    }

    routing {
        route("/api") {

            playersRoutes()
        }
    }
}
