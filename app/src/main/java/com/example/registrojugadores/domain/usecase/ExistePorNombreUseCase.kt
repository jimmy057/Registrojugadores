package com.example.registrojugadores.domain.usecase


import com.example.registrojugadores.domain.repository.JugadorRepository
import javax.inject.Inject

class ExistePorNombreUseCase @Inject constructor(
    private val repository: JugadorRepository
) {
    suspend operator fun invoke(nombre: String): Boolean {
        return repository.existePorNombre(nombre)
    }
}