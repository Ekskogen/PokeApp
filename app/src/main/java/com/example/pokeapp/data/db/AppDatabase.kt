package com.example.pokeapp.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokeapp.data.models.Pokemon

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance
                ?: synchronized(this) { instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "pokemon")
                .fallbackToDestructiveMigration()
                .build()
    }

}