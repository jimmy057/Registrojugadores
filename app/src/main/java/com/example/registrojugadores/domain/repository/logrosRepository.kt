package com.example.registrojugadores.domain.repository

import com.example.registrojugadores.domain.model.Logro
import kotlinx.coroutines.flow.Flow

interface LogroRepository {
    suspend fun insertLogro(logro: Logro)
    suspend fun deleteLogro(logro: Logro)
    fun getAllLogros(): Flow<List<Logro>>
    fun getLogrosByJugador(jugadorId: Int): Flow<List<Logro>>
}
