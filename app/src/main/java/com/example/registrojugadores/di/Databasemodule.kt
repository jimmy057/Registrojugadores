package com.example.registrojugadores.di

import android.content.Context
import androidx.room.Room
import com.example.registrojugadores.data.local.dao.JugadorDao
import com.example.registrojugadores.data.local.dao.LogroDao
import com.example.registrojugadores.data.local.dao.PartidaDao
import com.example.registrojugadores.data.local.databases.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Databasemodule {

    @Provides
    @Singleton
    fun providedatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "registro_jugadores_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideJugadorDao(db: AppDatabase): JugadorDao = db.jugadorDao()
    @Provides fun provideLogroDao(db: AppDatabase): LogroDao = db.logroDao()
    @Provides fun providePartidaDao(db: AppDatabase): PartidaDao = db.partidaDao()
}