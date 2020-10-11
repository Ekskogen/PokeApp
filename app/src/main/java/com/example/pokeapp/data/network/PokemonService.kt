package com.example.pokeapp.data.network

import com.example.pokeapp.data.models.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int = 20, @Query("offset") offset: Int = 0): PokemonResponse
}