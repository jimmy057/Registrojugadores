package com.example.registrojugadores.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.registrojugadores.data.local.Converters
import com.example.registrojugadores.data.local.dao.JugadorDao
import com.example.registrojugadores.data.local.dao.PartidaDao
import com.example.registrojugadores.data.local.dao.LogroDao
import com.example.registrojugadores.data.local.entities.JugadorEntity
import com.example.registrojugadores.data.local.entities.PartidaEntity
import com.example.registrojugadores.data.local.entities.LogroEntity

@Database(
    entities = [JugadorEntity::class, PartidaEntity::class, LogroEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jugadorDao(): JugadorDao
    abstract fun logroDao(): LogroDao
    abstract fun partidaDao(): PartidaDao

    companion object {
        const val DATABASE_NAME = "registro_jugadores_db"
    }
}

