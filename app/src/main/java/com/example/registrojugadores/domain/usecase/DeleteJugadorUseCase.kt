package com.example.registrojugadores.domain.usecase


import com.example.registrojugadores.domain.repository.JugadorRepository

class DeleteJugadorUseCase(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}