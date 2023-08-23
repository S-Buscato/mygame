package com.playeranking

import com.mongodb.reactivestreams.client.MongoDatabase
import com.playeranking.plugins.configureMonitoring
import com.playeranking.plugins.configureRouting
import com.playeranking.plugins.configureSerialization
import com.playeranking.routeServices.PlayersRouteService
import com.playeranking.services.PlayerService
import com.playeranking.services.impl.PlayerServiceImpl
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.koin.ktor.plugin.Koin
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    val logger: Logger = LoggerFactory.getLogger(Application::class.java)
    logger.info("Mygame start")

    val server = embeddedServer(CIO, port = 8080, module = Application::module)
    server.start(wait = true)

    logger.info("Mygame stopped")
}

fun Application.module() {

    val app = org.koin.dsl.module {

        single { PlayerServiceImpl() as PlayerService }
        single { PlayersRouteService(get()) }
    }

    fun MongoDatabase.asCoroutine(): CoroutineDatabase {
        return CoroutineDatabase(this)
    }

    install(Koin) {
        modules(app)
        configureMonitoring()
        configureSerialization()
        //configureSecurity()
        configureRouting()
    }

}


