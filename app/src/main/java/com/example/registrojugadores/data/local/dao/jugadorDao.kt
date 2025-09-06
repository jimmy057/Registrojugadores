package com.example.registrojugadores.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert
import androidx.room.Query
import com.example.registrojugadores.data.local.entities.JugadorEntity
import kotlinx.coroutines.flow.Flow

@Dao
    interface JugadorDao {
        @Query("SELECT * FROM Jugador ORDER BY JugadorId DESC")
        fun observeAll(): Flow<List<JugadorEntity>>

        @Query("SELECT * FROM Jugador WHERE JugadorId = :id")
        suspend fun getById(id: Int): JugadorEntity?

        @Upsert
        suspend fun upsert(entity: JugadorEntity)

        @Delete
        suspend fun delete(entity: JugadorEntity)

        @Query("DELETE FROM Jugador WHERE JugadorId = :id")
        suspend fun deleteById(id:Int)

    @Query("SELECT COUNT(*) > 0 FROM Jugador WHERE nombres = :nombre")
    suspend fun existePorNombre(nombre: String): Boolean
}