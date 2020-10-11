package com.example.pokeapp.di

import android.content.Context
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.domain.ClearPokemonTableUseCase
import com.example.pokeapp.domain.FetchPokemonsUseCase
import com.example.pokeapp.ui.main.MainActivity
import com.example.pokeapp.ui.main.MainContract
import com.example.pokeapp.ui.main.MainPresenter
import com.example.pokeapp.ui.main.epoxy.PokemonListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @ActivityScoped
    fun provideListener(@ActivityContext context: Context): PokemonListener = context as MainActivity

    @ActivityScoped
    @Provides
    fun provideMainPresenter(pokemonDataSource: DataSource.Factory<Int, Pokemon>,
                             pagedListConfig: PagedList.Config,
                             fetchPokemonsUseCase: FetchPokemonsUseCase,
                             clearPokemonTableUseCase: ClearPokemonTableUseCase
    ):
            MainContract.Presenter = MainPresenter(pokemonDataSource, pagedListConfig, fetchPokemonsUseCase, clearPokemonTableUseCase)
}