package com.playeranking.services

import com.playeranking.models.Player
import org.bson.conversions.Bson
import kotlin.test.Ignore

@Ignore
interface PlayerService {
    class PlayerAlreadyExistsException : Exception()
    class ScoreNotANumberException : Exception()
    class PlayerDoesNotExist : RuntimeException("Player does not exist")
    suspend fun getAllPlayers(): List<Player>
    suspend fun getPlayerByPseudo(pseudo: Bson): Player?
    suspend fun getAllPlayersSortedByScore(): List<Player>
    suspend fun savePlayer(player: Player): Boolean
    suspend fun updatePlayer(pseudo: String, player: Player): Boolean
    suspend fun replaceOneByPseudo(pseudo: String, player: Player): Boolean
    suspend fun deleteAllPlayers(): Boolean

}