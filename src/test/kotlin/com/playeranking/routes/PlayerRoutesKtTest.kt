package com.playeranking.routes

import com.playeranking.models.Player
import com.playeranking.routeServices.PlayersRouteService
import com.playeranking.services.PlayerService
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import org.bson.conversions.Bson
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.litote.kmongo.eq

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PlayerRoutesKtTest : KoinTest {

    private val playersRouteService = mockk<PlayersRouteService>()
    private val playerService = mockk<PlayerService>()

    @BeforeTest
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { playerService }
                single { PlayersRouteService(get()) }
            })
        }

        coEvery { playerService.savePlayer(any()) } returns true
        coEvery { playerService.getPlayerByPseudo(any()) } answers {
            val pseudo = it.invocation.args[0] as Bson
            if (pseudo == Player::pseudo eq "John") {
                Player("John", "00")
            } else {
                null
            }
        }
    }

    @AfterTest
    fun teardown() {
        stopKoin()
        clearAllMocks()
    }

    @Test
    fun testPostPlayer_PlayerAlreadyExists_Failure() = testApplication {
        val client: HttpClient = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val player = Player("John", "100")

        val response = client.post("/api/player") {
            contentType(ContentType.Application.Json)
            setBody(player)
        }

        assertEquals(HttpStatusCode.NotModified, response.status)
    }

    @Test
    fun testPostPlayer_InvalidScore_Failure() = testApplication {
        val client: HttpClient = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val player = Player("Alice", "not_a_number")

        coEvery { playersRouteService.playerIsExists(any()) } returns false
        coEvery { playerService.getPlayerByPseudo(any()) } returns null

        val response = client.post("/api/player") {
            contentType(ContentType.Application.Json)
            setBody(player)
        }

        assertEquals(HttpStatusCode.NotModified, response.status)
    }
}
