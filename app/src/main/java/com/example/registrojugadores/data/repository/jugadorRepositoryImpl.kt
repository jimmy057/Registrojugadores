package com.example.registrojugadores.data.repository

import com.example.registrojugadores.data.local.dao.JugadorDao
import com.example.registrojugadores.data.local.mapper.toDomain
import com.example.registrojugadores.data.local.mapper.toEntity
import com.example.registrojugadores.data.remote.JugadoresApi
import com.example.registrojugadores.data.remote.toEntity
import com.example.registrojugadores.domain.model.Jugador
import com.example.registrojugadores.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val dao: JugadorDao,
    private val api: JugadoresApi
) : JugadorRepository {

    override fun observeJugadores(): Flow<List<Jugador>> = flow {
        val localList = dao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
        emit(localList.single())

        try {
            val remote = api.getJugadores()
            val entities = remote.map { it.toEntity() }
            dao.upsertAll(entities)

            val updated = dao.observeAll().map { list -> list.map { it.toDomain() } }
            emit(updated.single())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getJugador(id: Int): Jugador? =
        dao.getById(id)?.toDomain()

    override suspend fun upsert(jugador: Jugador): Int {
        dao.upsert(jugador.toEntity())
        return jugador.jugadorId
    }

    override suspend fun delete(id: Int) = dao.deleteById(id)

    override suspend fun existePorNombre(nombre: String): Boolean =
        dao.existePorNombre(nombre)
}


