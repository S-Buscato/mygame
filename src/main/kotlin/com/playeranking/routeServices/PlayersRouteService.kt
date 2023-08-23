package com.playeranking.routeServices

import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import org.bson.conversions.Bson


class PlayersRouteService(private val playerService: PlayerService) {
    suspend fun getAllPlayers(): List<Player> {
        return playerService.getAllPlayers()
    }

    suspend fun getPlayerByPseudo(pseudo: Bson): Player? {
        return playerService.getPlayerByPseudo(pseudo)
    }
    suspend fun savePlayer(player: Player): Boolean {
        return playerService.savePlayer(player)
    }

    suspend fun getAllPlayersSortedByScore(): List<Player> {
        val players = playerService.getAllPlayers()

        return players.sortedByDescending { it.score.toInt() } // Tri décroissant par score
    }

    suspend fun updatePlayer(pseudo: String, updatedPlayer: Player): Boolean {
        val success = playerService.replaceOneByPseudo(pseudo, updatedPlayer)
        return success
    }

    suspend fun deleteAllPlayers(): Boolean {
        return playerService.deleteAllPlayers()
    }
    suspend fun playerIsExists(pseudo: Bson): Boolean {
        val player = playerService.getPlayerByPseudo(pseudo)
        return player != null
    }

}