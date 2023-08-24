package com.playeranking.routes

import com.playeranking.exception.PlayerRouteException
import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import com.playeranking.services.impl.PlayerServiceImpl
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
    val playerServiceImpl: PlayerService by inject()
    val logger: Logger = LoggerFactory.getLogger(Application::class.java)


    get("/players") {
        logger.info("route => getAllplayers")

        val players = playerServiceImpl.getAllPlayers()
        call.respond(players)
    }

    get("/players/{pseudo}") {

        val pseudo = call.parameters["pseudo"] ?: throw IllegalArgumentException("Player pseudo missing")
        logger.info("route => get/players/$pseudo")

        val player = playerServiceImpl.getPlayerByPseudo(Player::pseudo eq pseudo)
        if (player != null) {
            call.respond(player)
        } else {
            call.respondText("Player not found", status = HttpStatusCode.NotFound)
        }
    }

    get("/players/sortedByScore") {
        logger.info("route => get/players/sortedByScore")

        val playersSortedByScore = playerServiceImpl.getAllPlayersSortedByScore()
        call.respond(playersSortedByScore)
    }

    post("/player") {
        val player = call.receive<Player>()
        logger.info("route => post/players/ => ${player.pseudo} - ${player.score}")

        val response = try {
            val success = playerServiceImpl.savePlayer(player)
            if (success) {
                HttpStatusCode.Created to "Player saved successfully"
            } else {
                HttpStatusCode.InternalServerError to "Failed to save player"
            }
        } catch (e: PlayerService.PlayerAlreadyExistsException) {
            HttpStatusCode.NotModified to "Player ${player.pseudo} already exists"
        } catch (e: PlayerService.ScoreNotANumberException) {
            HttpStatusCode.NotModified to "Score ${player.score} must be a number"
        }

        call.respondText(response.second, status = response.first)
    }

    put("/players/update/{pseudo}") {
        val pseudo = call.parameters["pseudo"] ?: throw IllegalArgumentException("Player pseudo missing")
        val updatedPlayer = call.receive<Player>()
        logger.info("route => put/players/update/ => ${updatedPlayer.pseudo} - ${updatedPlayer.score}")


        if(updatedPlayer.pseudo.trim().length == 0 ) {
            HttpStatusCode.BadRequest to "A pseudo is mandatory"
        } else {
            val response = try {
                val success = playerServiceImpl.updatePlayer(pseudo, updatedPlayer)

                if (success) {
                    HttpStatusCode.OK to "Player updated successfully"
                } else {
                    HttpStatusCode.InternalServerError to "Failed to update player"
                }
            } catch (e: PlayerRouteException.PlayerDoesNotExist) {
                HttpStatusCode.NotFound to "Player ${updatedPlayer.pseudo} does not exist"
            } catch (e: PlayerRouteException.InvalidScore) {
                HttpStatusCode.NotModified to "Score ${updatedPlayer.score} must be a number"
            }
            call.respondText(response.second, status = response.first)
        }


    }

    delete("/players") {
        logger.info("route => delete/players")

        val success = playerServiceImpl.deleteAllPlayers()
        if (success) {
            call.respondText("All players deleted successfully")
        } else {
            call.respondText("Failed to delete players", status = HttpStatusCode.InternalServerError)
        }
    }

}