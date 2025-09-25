package com.example.registrojugadores.di

import com.example.registrojugadores.data.repository.JugadorRepositoryImpl
import com.example.registrojugadores.data.local.dao.JugadorDao
import com.example.registrojugadores.domain.repository.JugadorRepository
import com.example.registrojugadores.domain.usecase.DeleteJugadorUseCase
import com.example.registrojugadores.domain.usecase.GetJugadorUseCase
import com.example.registrojugadores.domain.usecase.ObserveJugadorUseCase
import com.example.registrojugadores.domain.usecase.UpsertJugadorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJugadorRepository(dao: JugadorDao): JugadorRepository =
        JugadorRepositoryImpl(dao)
    @Provides
    @Singleton
    fun provideGetJugadorUseCase(repo: JugadorRepository) = GetJugadorUseCase(repo)

    @Provides
    @Singleton
    fun provideObserveJugadorUseCase(repo: JugadorRepository) = ObserveJugadorUseCase(repo)

    @Provides
    @Singleton
    fun provideUpsertJugadorUseCase(repo: JugadorRepository) = UpsertJugadorUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteJugadorUseCase(repo: JugadorRepository) = DeleteJugadorUseCase(repo)
}
