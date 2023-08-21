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

}