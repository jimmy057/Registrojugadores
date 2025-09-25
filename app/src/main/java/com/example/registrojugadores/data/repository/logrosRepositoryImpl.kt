package com.example.registrojugadores.data.repository

import com.example.registrojugadores.data.local.dao.LogroDao
import com.example.registrojugadores.data.local.mapper.LogroMapper
import com.example.registrojugadores.domain.model.Logro
import com.example.registrojugadores.domain.repository.LogroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LogroRepositoryImpl(private val dao: LogroDao) : LogroRepository {
    override suspend fun insertLogro(logro: Logro) {
        dao.insertLogro(LogroMapper.fromDomain(logro))
    }

    override suspend fun deleteLogro(logro: Logro) {
        dao.deleteLogro(LogroMapper.fromDomain(logro))
    }

    override fun getAllLogros(): Flow<List<Logro>> =
        dao.getAllLogros().map { it.map(LogroMapper::toDomain) }

    override fun getLogrosByJugador(jugadorId: Int): Flow<List<Logro>> =
        dao.getLogrosByJugador(jugadorId).map { it.map(LogroMapper::toDomain) }
}

