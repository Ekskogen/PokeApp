package com.example.pokeapp.ui.main.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ViewholderPokemonBinding


@EpoxyModelClass(layout = R.layout.viewholder_loading)
abstract class LoadingModel: EpoxyModelWithHolder<LoadingModel.ViewHolder>() {

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
    }

    class ViewHolder: EpoxyHolder() {

        override fun bindView(itemView: View) {
        }
    }
}