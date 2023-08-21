package com.playeranking.controllers

import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import org.bson.conversions.Bson

class PlayersControllers(private val playerService: PlayerService) {
    suspend fun getAllPlayers(): List<Player> {
        return playerService.getAllPlayers()
    }

    suspend fun getPlayerByPseudo(pseudo: Bson): Player? {
        return playerService.getPlayerByPseudo(pseudo)
    }
}