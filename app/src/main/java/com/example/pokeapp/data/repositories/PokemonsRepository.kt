package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.db.PokemonDao
import com.example.pokeapp.data.network.END_OF_LIST
import com.example.pokeapp.data.network.NO_INTERNET
import com.example.pokeapp.data.network.NetworkResponse
import com.example.pokeapp.data.network.PokemonService
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

class PokemonsRepository @Inject constructor(val pokemonService: PokemonService, val pokemonDao: PokemonDao) {

    suspend fun fetchPokemons(offset: Int): NetworkResponse {
        return try {
            val pokemons = pokemonService.getPokemons(offset = offset).results
            if(pokemons.isEmpty())
                NetworkResponse.SUCCESS.apply { message = END_OF_LIST }
            else {
                pokemonDao.insertAll(pokemons)
                NetworkResponse.SUCCESS
            }
        } catch (e: Exception) {
            if(e is UnknownHostException) NetworkResponse.FAILED.apply { message = (NO_INTERNET) }
            else NetworkResponse.FAILED
        }
    }

    suspend fun getCountLocalPokemons(): Int {
        return pokemonDao.getAllCount()
    }

    suspend fun deleteAllPokemonRows() {
        pokemonDao.deleteAll()
    }
}