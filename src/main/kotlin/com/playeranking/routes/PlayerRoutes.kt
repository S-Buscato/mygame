package com.playeranking.routes

import com.playeranking.routeServices.PlayersRouteService
import com.playeranking.models.Player
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq


fun Route.playersRoutes() {
    val playersRouteService: PlayersRouteService by inject()

    get("/player") {
        val players = playersRouteService.getAllPlayers()
        call.respond(players)
    }

    get("/player/{pseudo}") {
        val pseudo = call.parameters["pseudo"] ?: throw IllegalArgumentException("Player pseudo missing")
        val player = playersRouteService.getPlayerByPseudo(Player::pseudo eq pseudo)
        if (player != null) {
            call.respond(player)
        } else {
            call.respondText("Player not found", status = HttpStatusCode.NotFound)
        }
    }

    post("/player") {
        val player = call.receive<Player>()
        val success = playersRouteService.savePlayer(player)
        if (success) {
            call.respondText("Player saved successfully", status = HttpStatusCode.Created)
        } else {
            call.respondText("Failed to save player", status = HttpStatusCode.InternalServerError)
        }
    }
}