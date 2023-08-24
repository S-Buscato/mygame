package com.playeranking.exception

import kotlin.test.Ignore

@Ignore
sealed class PlayerRouteException(message: String) : Exception(message) {
        class PlayerAlreadyExists : PlayerRouteException("Player already exists")
        class InvalidScore : PlayerRouteException("Score must be a number")
        class PlayerDoesNotExist : PlayerRouteException("PLayer Does Not Exist")
}
