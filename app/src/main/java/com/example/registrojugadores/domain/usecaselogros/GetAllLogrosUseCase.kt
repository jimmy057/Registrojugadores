package com.example.registrojugadores.domain.usecaselogros

import com.example.registrojugadores.domain.model.Logro
import com.example.registrojugadores.domain.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLogrosUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    operator fun invoke(): Flow<List<Logro>> {
        return repository.getAllLogros()
    }
}