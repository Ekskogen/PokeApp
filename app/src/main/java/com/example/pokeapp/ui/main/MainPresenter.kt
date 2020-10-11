package com.example.pokeapp.ui.main

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.network.END_OF_LIST
import com.example.pokeapp.data.network.NetworkResponse
import com.example.pokeapp.domain.ClearPokemonTableUseCase
import com.example.pokeapp.domain.FetchPokemonsUseCase
import com.example.pokeapp.ui.main.epoxy.PokemonListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainPresenter @Inject constructor(
    pokemonsDataSource: DataSource.Factory<Int, Pokemon>,
    pageListConfig: PagedList.Config,
    val fetchPokemonsUseCase: FetchPokemonsUseCase,
    val clearPokemonTableUseCase: ClearPokemonTableUseCase
): MainContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.IO
    override var view: MainContract.View? = null

    private val pokemonFeed: LiveData<PagedList<Pokemon>> =
        LivePagedListBuilder<Int, Pokemon>(pokemonsDataSource, pageListConfig)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Pokemon>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    fetchPokemonsFromZero()
                }
                override fun onItemAtEndLoaded(itemAtEnd: Pokemon) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    fetchPokemon(paging = true)
                }
            })
            .build()

    private val pokemonFeedObserver = Observer<PagedList<Pokemon>> {
        view?.showPokemonsList(it)
        view?.showLoadingBar(false)
        view?.stopLoadingSwipeToRefresh()
    }

    private fun fetchPokemonsFromZero() {
        fetchPokemon(false)
    }

    private fun fetchPokemon(paging: Boolean = false) {
        launch {
            val response = fetchPokemonsUseCase.execute(paging)
            withContext(Dispatchers.Main) {
                if(response == NetworkResponse.FAILED) {
                    view?.showLoadingBar(false)
                    view?.showErrorOnPokemonsList()
                }
                if(response == NetworkResponse.SUCCESS && response.message == END_OF_LIST) {
                    view?.showLoadingBar(false)
                    view?.showEndOfList()
                }
            }
        }
    }

    override fun startLoadingPokemons() {
        view?.showLoadingBar(true)
        pokemonFeed.observeForever(pokemonFeedObserver)
    }


    override fun refreshData() {
        launch {
            clearPokemonTableUseCase.execute()
        }
    }

    override fun takeView(v: MainContract.View) {
        view = v
    }

    override fun dropView() {
        view = null
        coroutineContext.cancel()
        pokemonFeed.removeObserver(pokemonFeedObserver)
    }
}