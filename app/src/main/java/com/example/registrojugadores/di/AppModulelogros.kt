package com.example.registrojugadores.di

import com.example.registrojugadores.data.repository.LogroRepositoryImpl
import com.example.registrojugadores.data.local.dao.LogroDao
import com.example.registrojugadores.domain.repository.LogroRepository
import com.example.registrojugadores.domain.usecaselogros.DeleteLogroUseCase
import com.example.registrojugadores.domain.usecaselogros.GetAllLogrosUseCase
import com.example.registrojugadores.domain.usecaselogros.GetLogrosByJugadorUseCase
import com.example.registrojugadores.domain.usecaselogros.InsertLogroUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModulelogros {

    @Provides
    @Singleton
    fun provideLogroRepository(dao: LogroDao): LogroRepository =
        LogroRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideInsertLogroUseCase(repo: LogroRepository) = InsertLogroUseCase(repo)

    @Provides
    @Singleton
    fun provideDeleteLogroUseCase(repo: LogroRepository) = DeleteLogroUseCase(repo)

    @Provides
    @Singleton
    fun provideGetAllLogrosUseCase(repo: LogroRepository) = GetAllLogrosUseCase(repo)

    @Provides
    @Singleton
    fun provideGetLogrosByJugadorUseCase(repo: LogroRepository) = GetLogrosByJugadorUseCase(repo)
}
