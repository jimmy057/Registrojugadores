package com.example.registrojugadores.di

import android.content.Context
import androidx.room.Room
import com.example.registrojugadores.data.local.dao.JugadorDao
import com.example.registrojugadores.data.local.dao.LogroDao
import com.example.registrojugadores.data.local.dao.PartidaDao
import com.example.registrojugadores.data.local.databases.AppDatabase
import com.example.registrojugadores.data.remote.JugadoresApi
import com.example.registrojugadores.data.repository.JugadorRepositoryImpl
import com.example.registrojugadores.data.repository.LogroRepositoryImpl
import com.example.registrojugadores.data.repository.PartidasRepositoryImpl
import com.example.registrojugadores.domain.repository.JugadorRepository
import com.example.registrojugadores.domain.repository.LogroRepository
import com.example.registrojugadores.domain.repository.PartidaRepository
import com.example.registrojugadores.domain.usecase.*
import com.example.registrojugadores.domain.usecaselogros.*
import com.example.registrojugadores.domain.usecasepartida.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAOs
    @Provides fun provideJugadorDao(db: AppDatabase): JugadorDao = db.jugadorDao()
    @Provides fun provideLogroDao(db: AppDatabase): LogroDao = db.logroDao()
    @Provides fun providePartidaDao(db: AppDatabase): PartidaDao = db.partidaDao()

    // Repositories
    @Provides
    @Singleton
    fun provideJugadorRepository(dao: JugadorDao, api: JugadoresApi): JugadorRepository =
        JugadorRepositoryImpl(dao, api)

    @Provides
    @Singleton
    fun provideLogroRepository(dao: LogroDao): LogroRepository =
        LogroRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePartidaRepository(dao: PartidaDao): PartidaRepository =
        PartidasRepositoryImpl(dao)

    // Use Cases Jugador
    @Provides @Singleton fun provideGetJugadorUseCase(repo: JugadorRepository) = GetJugadorUseCase(repo)
    @Provides @Singleton fun provideObserveJugadorUseCase(repo: JugadorRepository) = ObserveJugadorUseCase(repo)
    @Provides @Singleton fun provideUpsertJugadorUseCase(repo: JugadorRepository) = UpsertJugadorUseCase(repo)
    @Provides @Singleton fun provideDeleteJugadorUseCase(repo: JugadorRepository) = DeleteJugadorUseCase(repo)

    // Use Cases Logro
    @Provides @Singleton fun provideInsertLogroUseCase(repo: LogroRepository) = InsertLogroUseCase(repo)
    @Provides @Singleton fun provideDeleteLogroUseCase(repo: LogroRepository) = DeleteLogroUseCase(repo)
    @Provides @Singleton fun provideGetAllLogrosUseCase(repo: LogroRepository) = GetAllLogrosUseCase(repo)
    @Provides @Singleton fun provideGetLogrosByJugadorUseCase(repo: LogroRepository) = GetLogrosByJugadorUseCase(repo)

    // Use Cases Partida
    @Provides @Singleton fun provideInsertPartidaUseCase(repo: PartidaRepository) = InsertPartidaUseCase(repo)
    @Provides @Singleton fun provideDeletePartidaUseCase(repo: PartidaRepository) = DeletePartidaUseCase(repo)
    @Provides @Singleton fun provideGetAllPartidasUseCase(repo: PartidaRepository) = GetAllPartidasUseCase(repo)
    @Provides @Singleton fun provideGetPartidaUseCase(repo: PartidaRepository) = GetPartidaUseCase(repo)
    @Provides @Singleton fun provideObservePartidasUseCase(repo: PartidaRepository) = ObservePartidasUseCase(repo)
}






