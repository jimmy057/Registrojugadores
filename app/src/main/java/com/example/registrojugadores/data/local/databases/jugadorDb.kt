package com.example.registrojugadores.data.local.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrojugadores.data.local.entities.JugadorEntity
import com.example.registrojugadores.data.local.dao.JugadorDao


@Database(
    entities = [
        JugadorEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract  class JugadorDatabase : RoomDatabase(){
    abstract fun JugadorDao():JugadorDao
}