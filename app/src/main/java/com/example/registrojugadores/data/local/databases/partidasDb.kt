package com.example.registrojugadores.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrojugadores.data.local.dao.JugadorDao
import com.example.registrojugadores.data.local.dao.PartidaDao
import com.example.registrojugadores.data.local.entities.JugadorEntity
import com.example.registrojugadores.data.local.entities.PartidaEntity

@Database(entities = [JugadorEntity::class, PartidaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jugadorDao(): JugadorDao
    abstract fun partidaDao(): PartidaDao
}