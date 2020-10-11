package com.example.pokeapp.data.network

enum class NetworkResponse(var message: String? = null) {
    SUCCESS,
    FAILED
}

// MESSAGES
val NO_INTERNET = "nointernet"
val END_OF_LIST = "endoflist"