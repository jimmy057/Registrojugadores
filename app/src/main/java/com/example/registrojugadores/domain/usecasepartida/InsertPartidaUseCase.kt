package com.example.registrojugadores.domain.usecasepartida

import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import javax.inject.Inject

class InsertPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida) {
        repository.insertPartida(partida)
    }
}