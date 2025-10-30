package com.example.registrojugadores.data.remote

import com.example.registrojugadores.data.local.entities.JugadorEntity

data class JugadorDto(
    val jugadorId: Int,
    val nombres: String,
    val partidas: Int,
    val logros: List<String>?
)

fun JugadorDto.toEntity() = JugadorEntity(
    jugadorId = jugadorId,
    nombres = nombres,
    partidas = partidas,
    logros = logros ?: emptyList()
)
