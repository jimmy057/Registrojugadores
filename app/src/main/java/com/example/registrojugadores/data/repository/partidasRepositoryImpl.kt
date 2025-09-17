package com.example.registrojugadores.data.repository

import com.example.registrojugadores.data.local.dao.PartidaDao
import com.example.registrojugadores.data.local.mapper.PartidaMapper
import com.example.registrojugadores.domain.model.Partida
import com.example.registrojugadores.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartidasRepositoryImpl @Inject constructor(
    private val dao: PartidaDao
) : PartidaRepository {

    override fun getPartidas(): Flow<List<Partida>> =
        dao.getAllPartidas().map { list -> list.map { PartidaMapper.toDomain(it) } }

    override suspend fun getPartidaById(id: Int): Partida? =
        dao.getPartidaById(id)?.let { PartidaMapper.toDomain(it) }

    override suspend fun insertPartida(partida: Partida) =
        dao.insertPartida(PartidaMapper.fromDomain(partida))

    override suspend fun deletePartida(partida: Partida) =
        dao.deletePartida(PartidaMapper.fromDomain(partida))
}

