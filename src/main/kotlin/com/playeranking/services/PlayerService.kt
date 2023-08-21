package com.playeranking.services

import com.playeranking.models.Player
import org.bson.conversions.Bson

interface PlayerService {
    suspend fun getAllPlayers(): List<Player>
    suspend fun getPlayerByPseudo(id: Bson): Player?
    suspend fun savePlayer(player: Player): Boolean


}