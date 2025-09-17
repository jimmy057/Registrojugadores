package com.example.registrojugadores.domain.usecasepartida

import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import javax.inject.Inject

class DeletePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida) {
        repository.deletePartida(partida)
    }
}