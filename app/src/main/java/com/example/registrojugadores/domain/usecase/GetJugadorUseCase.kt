package com.example.registrojugadores.domain.usecase

import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.repository.JugadorRepository

class GetJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int): Jugador? = repository.getJugador(id)
}