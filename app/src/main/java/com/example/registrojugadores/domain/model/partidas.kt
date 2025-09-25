package com.example.registrojugadores.domain.model

import androidx.room.Entity

@Entity(tableName = "partidas")
data class Partida(
    val partidaId: Int = 0,
    val fecha: String,
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int?,
    val esFinalizada: Boolean,
    val currentPlayerId: Int? = null,
    val board: List<Int?> = List(9) { null }
)