package com.playeranking.serviceTest

import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase

class PlayerServiceTest : KoinTest {
    private val database: CoroutineDatabase = mockk()
    private val playerCollection: CoroutineCollection<Player> = mockk()
    private val playerService: PlayerService by inject()

    companion object {
        @BeforeAll
        @JvmStatic
        fun setUp() {
            startKoin { /* Initialisation de Koin ici */ }
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            stopKoin() // Fermez Koin après les tests
        }
    }
    @BeforeEach
    fun setUp() {
        coEvery { database.getCollection<Player>("players") } returns playerCollection
    }

    @Test
    fun `test getting all players`() = runBlocking {
        // Mock the behavior of the collection
        coEvery { playerCollection.find().toList() } returns listOf(Player("player1", "100", 1))

        val players = playerService.getAllPlayers()

        // Assertions
        assert(players.size == 1)
        assert(players[0].pseudo == "player1")
    }

    // Write similar tests for other functions of PlayerService
}