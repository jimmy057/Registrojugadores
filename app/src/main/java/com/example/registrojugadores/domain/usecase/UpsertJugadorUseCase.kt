package com.example.registrojugadores.domain.usecase

import kotlinx.coroutines.flow.first
import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.repository.JugadorRepository

class UpsertJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(jugador: Jugador): Result<Int> {
        val existingJugadores = repository.observeJugadores()
            .first()

        val validationResult = validateJugadorUi(jugador.nombres, jugador.partidas.toString())
        if (!validationResult.isValid) {
            return Result.failure(IllegalArgumentException(validationResult.nombresError))
        }

        return runCatching { repository.upsert(jugador) }
    }
}