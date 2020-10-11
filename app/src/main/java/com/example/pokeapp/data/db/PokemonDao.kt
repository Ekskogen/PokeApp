package com.example.pokeapp.data.db

import androidx.paging.DataSource
import androidx.room.*
import com.example.pokeapp.data.models.Pokemon

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAll(): List<Pokemon>

    @Query("SELECT count(*) FROM pokemon")
    suspend fun getAllCount(): Int

    @Query("SELECT * FROM pokemon")
    fun dataSource(): DataSource.Factory<Int, Pokemon>

    @Query("SELECT * FROM pokemon WHERE name IN (:name)")
    fun findByName(name: String): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE name LIKE :name AND " +
            "url LIKE :url LIMIT 1")
    fun findByNameAndUrl(name: String, url: String): Pokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: Pokemon)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemons: List<Pokemon>)

    @Delete
    fun delete(pokemon: Pokemon)

    @Query("DELETE FROM pokemon")
    fun deleteAll()
}