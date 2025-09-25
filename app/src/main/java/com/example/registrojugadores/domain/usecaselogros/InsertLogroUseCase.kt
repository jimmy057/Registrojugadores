package com.example.registrojugadores.domain.usecaselogros

import com.example.registrojugadores.domain.model.Logro
import com.example.registrojugadores.domain.repository.LogroRepository
import javax.inject.Inject

class InsertLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(logro: Logro) {
        repository.insertLogro(logro)
    }
}