package com.example.registrojugadores.di

import android.content.Context
import androidx.room.Room
import com.example.registrojugadores.data.local.dao.PartidaDao
import com.example.registrojugadores.data.local.databases.AppDatabase
import com.example.registrojugadores.data.repository.PartidasRepositoryImpl
import com.example.registrojugadores.domain.repository.PartidaRepository
import com.example.registrojugadores.domain.usecasepartida.DeletePartidaUseCase
import com.example.registrojugadores.domain.usecasepartida.GetAllPartidasUseCase
import com.example.registrojugadores.domain.usecasepartida.GetPartidaUseCase
import com.example.registrojugadores.domain.usecasepartida.InsertPartidaUseCase
import com.example.registrojugadores.domain.usecasepartida.ObservePartidasUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object AppModulepartidas {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()

    @Provides
    fun providePartidaDao(db: AppDatabase) = db.partidaDao()

    @Provides
    @Singleton
    fun providePartidaRepository(dao: PartidaDao): PartidaRepository =
        PartidasRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideInsertPartidaUseCase(repo: PartidaRepository) = InsertPartidaUseCase(repo)

    @Provides
    @Singleton
    fun provideDeletePartidaUseCase(repo: PartidaRepository) = DeletePartidaUseCase(repo)

    @Provides
    @Singleton
    fun provideGetAllPartidasUseCase(repo: PartidaRepository) = GetAllPartidasUseCase(repo)

    @Provides
    @Singleton
    fun provideGetPartidaUseCase(repo: PartidaRepository) = GetPartidaUseCase(repo)

    @Provides
    @Singleton
    fun provideObservePartidasUseCase(repo: PartidaRepository) = ObservePartidasUseCase(repo)
}