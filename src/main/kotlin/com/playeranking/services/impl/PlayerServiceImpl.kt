package com.playeranking.services.impl

import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import org.bson.conversions.Bson
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class PlayerServiceImpl : PlayerService {
    private val database: CoroutineDatabase = KMongo.createClient("mongodb://localhost:27017").coroutine.getDatabase("mygame")
    private val playerCollection = database.getCollection<Player>("player")

    override suspend fun getAllPlayers(): List<Player> {
        return playerCollection.find().toList()
    }

    override suspend fun getPlayerByPseudo(pseudo: Bson): Player? {
        return playerCollection.findOne(pseudo)    }

    override suspend fun savePlayer(player: Player): Boolean {
        val result = playerCollection.insertOne(player)
        return result.wasAcknowledged()    }

    override suspend fun replaceOneByPseudo(pseudo: String, updatedPlayer: Player): Boolean {
        val result = playerCollection.replaceOne(Player::pseudo eq pseudo, updatedPlayer)
        return result.wasAcknowledged()
    }

    override suspend fun deleteAllPlayers(): Boolean {
        val result = playerCollection.deleteMany("{}")
        return result.wasAcknowledged()
    }

}