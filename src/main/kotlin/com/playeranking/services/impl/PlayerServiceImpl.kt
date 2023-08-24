package com.playeranking.services.impl

import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import org.bson.conversions.Bson
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.json
import org.litote.kmongo.reactivestreams.KMongo

class PlayerServiceImpl() : PlayerService {
    private val database: CoroutineDatabase = KMongo.createClient("mongodb://localhost:27017").coroutine.getDatabase("mygame")
    private val playerCollection = this.database.getCollection<Player>("player")

    override suspend fun getAllPlayers(): List<Player> {
        return playerCollection.find().toList()
    }

    override suspend fun getPlayerByPseudo(pseudo: Bson): Player? {
        return  playerCollection.findOne(pseudo)
    }

    override suspend fun getAllPlayersSortedByScore(): List<Player> {
        val players = getAllPlayers()
        return players.sortedByDescending { it.score.toInt() }
    }

    override suspend fun savePlayer(player: Player): Boolean {
        val isExist = playerIsExists(Player::pseudo eq player.pseudo)
        val score: Int? = player.score.toIntOrNull()

        if (isExist) {
            throw PlayerService.PlayerAlreadyExistsException()
        } else if (score == null) {
            throw PlayerService.ScoreNotANumberException()
        } else {
            val result = playerCollection.insertOne(player)
            return result.wasAcknowledged()
        }
    }

    override suspend fun updatePlayer(pseudo: String, player: Player): Boolean {
        val isExist = playerIsExists(Player::pseudo eq pseudo)
        val score: Int? = player.score.toIntOrNull()

        if (!isExist) {
            throw PlayerService.PlayerDoesNotExist()
        } else if (score == null) {
            throw PlayerService.ScoreNotANumberException()
        } else {
            val success = replaceOneByPseudo(pseudo, player)
            return success
        }
    }

    override suspend fun replaceOneByPseudo(pseudo: String, updatedPlayer: Player): Boolean {
        val result = playerCollection.replaceOne(Player::pseudo eq pseudo, updatedPlayer)
        return result.wasAcknowledged()
    }

    override suspend fun deleteAllPlayers(): Boolean {
        val result = playerCollection.deleteMany("{}")
        return result.wasAcknowledged()
    }

    suspend fun playerIsExists(pseudo: Bson): Boolean {
        val player = playerCollection.findOne(pseudo)
        return player != null
    }


}