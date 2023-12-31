package com.playeranking

import com.mongodb.reactivestreams.client.MongoDatabase
import com.playeranking.plugins.configureMonitoring
import com.playeranking.plugins.configureRouting
import com.playeranking.plugins.configureSerialization
import com.playeranking.services.PlayerService
import com.playeranking.services.impl.PlayerServiceImpl
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext
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
        install(ContentNegotiation) {
            json(Json {
                // Configurez ici les paramètres de sérialisation si nécessaire
                prettyPrint = true // Par exemple, pour une mise en forme lisible
            })
        }

        single { PlayerServiceImpl() as PlayerService }
    }

    fun MongoDatabase.asCoroutine(): CoroutineDatabase {
        return CoroutineDatabase(this)
    }

    if (GlobalContext.getOrNull() == null) {
        install(Koin) {
            modules(app)
            configureMonitoring()
            configureSerialization()
            //configureSecurity()
            configureRouting()
        }
    }


}


