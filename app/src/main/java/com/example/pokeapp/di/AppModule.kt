package com.example.pokeapp.di

import android.content.Context
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.pokeapp.data.db.AppDatabase
import com.example.pokeapp.data.db.PokemonDao
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.network.PokemonService
import com.example.pokeapp.data.repositories.PokemonsRepository
import com.example.pokeapp.domain.ClearPokemonTableUseCase
import com.example.pokeapp.domain.FetchPokemonsUseCase
import com.example.pokeapp.ui.main.MainActivity
import com.example.pokeapp.ui.main.MainContract
import com.example.pokeapp.ui.main.MainPresenter
import com.example.pokeapp.ui.main.epoxy.PokemonListener
import com.example.pokeapp.ui.main.epoxy.PokemonsFeedController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun providePageListConfig(): PagedList.Config = PagedList.Config.Builder()
        .setPageSize(10)
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(1)
        .setEnablePlaceholders(true).build()

    @Provides
    fun provideFetchPokemonsUseCase(repository: PokemonsRepository) = FetchPokemonsUseCase(repository)

    @Provides
    fun provideClearPokemonsUseCase(repository: PokemonsRepository) = ClearPokemonTableUseCase(repository)

    @Provides
    fun provideRepository(pokemonDao: PokemonDao, pokemonService: PokemonService) = PokemonsRepository(pokemonService, pokemonDao)
}
