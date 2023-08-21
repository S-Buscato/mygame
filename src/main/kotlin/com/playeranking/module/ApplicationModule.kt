package com.playeranking.module

import com.mongodb.reactivestreams.client.MongoDatabase
import com.playeranking.controllers.PlayersControllers
import com.playeranking.services.PlayerService
import com.playeranking.services.impl.PlayerServiceImpl
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase

val applicationModule = module {

    single { PlayerServiceImpl() as PlayerService }
    single { PlayersControllers(get()) }
}

fun MongoDatabase.asCoroutine(): CoroutineDatabase {
    return CoroutineDatabase(this)
}