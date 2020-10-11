package com.example.pokeapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(@PrimaryKey val name: String, val url: String) {
}