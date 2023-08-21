package com.playeranking.models

import kotlinx.serialization.Serializable

@Serializable
data class Player (
    val pseudo: String,
    val score: String,
    val rank: Int
    )
