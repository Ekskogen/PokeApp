package com.example.pokeapp.domain

import com.example.pokeapp.data.network.NetworkResponse
import com.example.pokeapp.data.repositories.PokemonsRepository
import javax.inject.Inject

class FetchPokemonsUseCase @Inject constructor(val repository: PokemonsRepository) {

    suspend fun execute(paging: Boolean = false): NetworkResponse {
        var offset = 0
        if (paging) offset = repository.getCountLocalPokemons()
        return repository.fetchPokemons(offset)
    }
}