package com.example.registrojugadores.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Jugador(
    val jugadorId: Int = 0,
    val nombres: String,
    val partidas: Int
)