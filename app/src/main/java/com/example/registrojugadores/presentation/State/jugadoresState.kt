package com.example.registrojugadores.presentation.State

import com.example.registrojugadores.domain.model.Jugador

data class JugadoresState(
    val jugadores: List<Jugador> = emptyList(),
    val navegarAEditar: Int? = null
)
