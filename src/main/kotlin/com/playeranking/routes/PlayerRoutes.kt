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
import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun Route.playersRoutes() {
    val playersRouteService: PlayersRouteService by inject()
    val logger: Logger = LoggerFactory.getLogger(Application::class.java)


    get("/players") {
        logger.info("route => getAllplayers")

        val players = playersRouteService.getAllPlayers()
        call.respond(players)
    }

    get("/players/{pseudo}") {

        val pseudo = call.parameters["pseudo"] ?: throw IllegalArgumentException("Player pseudo missing")
        logger.info("route => get/players/$pseudo")

        val player = playersRouteService.getPlayerByPseudo(Player::pseudo eq pseudo)
        if (player != null) {
            call.respond(player)
        } else {
            call.respondText("Player not found", status = HttpStatusCode.NotFound)
        }
    }

    get("/players/sortedByScore") {
        logger.info("route => get/players/sortedByScore")

        val playersSortedByScore = playersRouteService.getAllPlayersSortedByScore()
        call.respond(playersSortedByScore)
    }

    post("/player") {
        val player = call.receive<Player>()
        logger.info("route => post/players/ => ${player.pseudo} - ${player.score}")

        val isExist = playersRouteService.playerIsExists(Player::pseudo eq player.pseudo)

            val score : Int? = player.score.toIntOrNull()

            if(isExist) {
                call.respondText("Player all ready exists", status = HttpStatusCode.NotModified)
            } else if (score == null) {
                call.respondText("Score must be a number", status = HttpStatusCode.NotModified)
            } else {
                val success = playersRouteService.savePlayer(player)
                if (success) {
                    call.respondText("Player saved successfully", status = HttpStatusCode.Created)
                } else {
                    call.respondText("Failed to save player", status = HttpStatusCode.InternalServerError)
                }
            }
    }

    put("/players/update/{pseudo}") {
        val pseudo = call.parameters["pseudo"] ?: throw IllegalArgumentException("Player pseudo missing")
        val updatedPlayer = call.receive<Player>()
        logger.info("route => put/players/update/ => ${updatedPlayer.pseudo} - ${updatedPlayer.score}")

        val isExist = playersRouteService.playerIsExists(Player::pseudo eq pseudo)

        if(isExist) {
            val success = playersRouteService.updatePlayer(pseudo, updatedPlayer)

            if (success) {
                call.respondText("Player updated successfully")
            } else {
                call.respondText("Failed to update player", status = HttpStatusCode.InternalServerError)
            }
        } else {
            call.respondText("Player does not exists", status = HttpStatusCode.NotFound)
        }
    }

    delete("/players") {
        logger.info("route => delete/players")

        val success = playersRouteService.deleteAllPlayers()
        if (success) {
            call.respondText("All players deleted successfully")
        } else {
            call.respondText("Failed to delete players", status = HttpStatusCode.InternalServerError)
        }
    }

}