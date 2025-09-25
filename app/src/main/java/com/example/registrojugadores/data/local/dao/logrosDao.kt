package com.example.registrojugadores.data.local.dao

import androidx.room.*
import com.example.registrojugadores.data.local.entities.LogroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogro(logro: LogroEntity)

    @Query("SELECT * FROM logros WHERE jugadorId = :jugadorId")
    fun getLogrosByJugador(jugadorId: Int): Flow<List<LogroEntity>>

    @Query("SELECT * FROM logros")
    fun getAllLogros(): Flow<List<LogroEntity>>

    @Delete
    suspend fun deleteLogro(logro: LogroEntity)
}
