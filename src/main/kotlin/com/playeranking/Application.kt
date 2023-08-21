package com.playeranking

import com.playeranking.module.applicationModule
import com.playeranking.plugins.configureMonitoring
import com.playeranking.plugins.configureRouting
import com.playeranking.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    embeddedServer(CIO, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(applicationModule)
    }
        configureMonitoring()
        configureSerialization()
        //configureSecurity()
        configureRouting()
}


