package com.example.registrojugadores.data.local.dao

import androidx.room.*
import com.example.registrojugadores.data.local.entities.PartidaEntity
import com.example.registrojugadores.data.local.mapper.PartidaMapper
import com.example.registrojugadores.domain.model.Partida
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao {
    @Query("SELECT * FROM partidas ORDER BY partidaId DESC")
    fun getAllPartidas(): Flow<List<PartidaEntity>>

    @Query("SELECT * FROM partidas WHERE partidaId = :id")
    suspend fun getPartidaById(id: Int): PartidaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartida(partida: PartidaEntity): Long

    @Update
    suspend fun updatePartida(partida: PartidaEntity)

    @Delete
    suspend fun deletePartida(partida: PartidaEntity)

    @Query("SELECT * FROM partidas WHERE esFinalizada = 0 ORDER BY partidaId DESC LIMIT 1")
    suspend fun obtenerUltimaPartidaEnCurso(): PartidaEntity?
}