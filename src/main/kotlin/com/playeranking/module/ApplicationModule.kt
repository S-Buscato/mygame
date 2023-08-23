package com.playeranking.module

import com.mongodb.reactivestreams.client.MongoDatabase
import com.playeranking.routeServices.PlayersRouteService
import com.playeranking.services.impl.PlayerServiceImpl
import org.junit.Ignore
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase

@get:Ignore
val applicationModule = module {

    single { PlayerServiceImpl() }
    single { PlayersRouteService(get()) }
}

fun MongoDatabase.asCoroutine(): CoroutineDatabase {
    return CoroutineDatabase(this)
}