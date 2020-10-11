package com.example.pokeapp.domain

import com.example.pokeapp.data.repositories.PokemonsRepository

class ClearPokemonTableUseCase(val repository: PokemonsRepository) {

    suspend fun execute() {
        repository.deleteAllPokemonRows()
    }
}