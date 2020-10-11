package com.example.pokeapp.ui.main.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.ui.main.MainContract
import com.example.pokeapp.ui.main.MainPresenter
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class PokemonsFeedController @Inject constructor(val listener: PokemonListener): PagedListEpoxyController<Pokemon>() {

    var endReached = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: Pokemon?): EpoxyModel<*> {
        return if (item == null)
            PokemonModel_()
                .name("   ")
                .url("   ")
                .id(currentPosition)
        else
            PokemonModel_()
                .name(item.name)
                .url(item.url)
                .listener(listener)
                .id(item.name)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        super.addModels(models)
        LoadingModel_()
            .id("loading")
            .addIf(!endReached && models.isNotEmpty(), this)

        EndModel_()
            .id("end")
            .addIf(endReached, this)
    }
}