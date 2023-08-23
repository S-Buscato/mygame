package com.playeranking.routeServices

import com.mongodb.client.model.Filters.eq
import com.playeranking.models.Player
import com.playeranking.services.PlayerService
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.bson.conversions.Bson
import kotlin.test.BeforeTest
import kotlin.test.AfterTest

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


internal class PlayersRouteServiceTest {

    private var playersRouteService = mockk<PlayersRouteService>()
    private var playerService = mockk<PlayerService>()


    @BeforeTest
    fun setUp() {
        @MockK
        playerService = mockk()
        @MockK
        playersRouteService = PlayersRouteService(playerService)
    }

    @AfterTest
    fun tearDown() {
        // Clear all mocks after each test
        clearAllMocks()
    }

    @Test
    fun testGetAllPlayers() = runBlocking {
        val players = listOf(Player("John", "100"), Player("Alice", "200"))
        coEvery { playerService.getAllPlayers() } returns players

        val result = playersRouteService.getAllPlayers()

        assertEquals(players, result)
        coVerify(exactly = 1) { playerService.getAllPlayers() }
    }

    @Test
    fun testPlayerIsExists_Exists() = runBlocking {
        val pseudo: Bson = eq("John")
        coEvery { playerService.getPlayerByPseudo(pseudo) } returns Player("John", "100")

        val result = playersRouteService.playerIsExists(pseudo)

        assertTrue(result)
        coVerify(exactly = 1) { playerService.getPlayerByPseudo(pseudo) }
    }

    @Test
    fun testPlayerIsExists_NotExists() = runBlocking {
        val pseudo: Bson = eq("Alice")
        coEvery { playerService.getPlayerByPseudo(pseudo) } returns null

        val result = playersRouteService.playerIsExists(pseudo)

        assertFalse(result)
        coVerify(exactly = 1) { playerService.getPlayerByPseudo(pseudo) }
    }

    @Test
    fun testSavePlayer() = runBlocking {
        val player = Player("John", "100")
        coEvery { playerService.savePlayer(player) } returns true

        val result = playersRouteService.savePlayer(player)

        assertEquals(true, result)
        coVerify(exactly = 1) { playerService.savePlayer(player) }
    }

    @Test
    fun testGetAllPlayersSortedByScore() = runBlocking {
        val players = listOf(Player("John", "100"), Player("Alice", "200"))
        coEvery { playerService.getAllPlayers() } returns players

        val result = playersRouteService.getAllPlayersSortedByScore()

        val expectedSortedPlayers = players.sortedByDescending { it.score.toInt() }
        assertEquals(expectedSortedPlayers, result)
        coVerify(exactly = 1) { playerService.getAllPlayers() }
    }

    @Test
    fun testUpdatePlayer() = runBlocking {
        val updatedPlayer = Player("John", "150")
        coEvery { playerService.replaceOneByPseudo("John", updatedPlayer) } returns true

        val result = playersRouteService.updatePlayer("John", updatedPlayer)

        assertEquals(true, result)
        coVerify(exactly = 1) { playerService.replaceOneByPseudo("John", updatedPlayer) }
    }

    @Test
    fun testDeleteAllPlayers() = runBlocking {
        coEvery { playerService.deleteAllPlayers() } returns true

        val result = playersRouteService.deleteAllPlayers()

        assertEquals(true, result)
        coVerify(exactly = 1) { playerService.deleteAllPlayers() }
    }
}