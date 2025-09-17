package com.example.registrojugadores.domain.usecasepartida

import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPartidasUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    operator fun invoke(): Flow<List<Partida>> {
        return repository.getPartidas()
    }
}
