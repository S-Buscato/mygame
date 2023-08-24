package com.playeranking.module

import com.mongodb.reactivestreams.client.MongoDatabase
import com.playeranking.services.impl.PlayerServiceImpl
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import kotlin.test.Ignore

@get:Ignore
val applicationModule = module {

    single { PlayerServiceImpl() }
}

fun MongoDatabase.asCoroutine(): CoroutineDatabase {
    return CoroutineDatabase(this)
}