package com.example.pokeapp.ui.main

import androidx.paging.PagedList
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.ui.utils.BasePresenter
import com.example.pokeapp.ui.utils.BaseView

interface MainContract {

    interface View: BaseView<Presenter> {
        fun showPokemonsList(pokemons: PagedList<Pokemon>)
        fun showLoadingBar(show: Boolean)
        fun stopLoadingSwipeToRefresh()
        fun showErrorOnPokemonsList()
        fun showEndOfList()
    }

    interface Presenter: BasePresenter<View> {
        fun startLoadingPokemons()
        fun refreshData()
    }
}