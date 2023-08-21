package com.playeranking.routes

import com.playeranking.controllers.PlayersControllers
import com.playeranking.models.Player
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq


fun Route.playersRoutes() {
    val playerRoutes: PlayersControllers by inject()

    get("/player") {
        val players = playerRoutes.getAllPlayers()
        call.respond(players)
    }

    get("/player/{pseudo}") {
        val pseudo = call.parameters["pseudo"] ?: throw IllegalArgumentException("Player pseudo missing")
        val player = playerRoutes.getPlayerByPseudo(Player::pseudo eq pseudo)
        if (player != null) {
            call.respond(player)
        } else {
            call.respondText("Player not found", status = HttpStatusCode.NotFound)
        }
    }
}