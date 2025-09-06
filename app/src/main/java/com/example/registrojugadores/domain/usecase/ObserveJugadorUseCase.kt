package com.example.registrojugadores.domain.usecase

import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow

class ObserveJugadorUseCase(
    private val repository: JugadorRepository
) {
    operator fun invoke(): Flow<List<Jugador>> = repository.observeJugadores()
}