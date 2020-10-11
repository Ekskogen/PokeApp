package com.example.pokeapp.di

import android.content.Context
import androidx.paging.DataSource
import com.example.pokeapp.data.db.AppDatabase
import com.example.pokeapp.data.db.PokemonDao
import com.example.pokeapp.data.models.Pokemon
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = AppDatabase.getDatabase(context)

    @Provides
    fun providePokemonDao(db: AppDatabase) = db.pokemonDao()

    @Provides
    fun providePokemonDataSource(pokemonDao: PokemonDao): DataSource.Factory<Int, Pokemon> =
        pokemonDao.dataSource()

}