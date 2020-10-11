package com.example.pokeapp.ui.utils

import kotlinx.coroutines.CoroutineScope

interface BasePresenter<V>: CoroutineScope {
    var view: V?
    fun takeView(v: V)
    fun dropView()
}