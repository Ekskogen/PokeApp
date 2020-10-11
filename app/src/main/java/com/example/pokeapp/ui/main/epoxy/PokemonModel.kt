package com.example.pokeapp.ui.main.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ViewholderPokemonBinding
import com.example.pokeapp.ui.main.MainContract
import kotlinx.android.synthetic.main.viewholder_pokemon.view.*


@EpoxyModelClass(layout = R.layout.viewholder_pokemon)
abstract class PokemonModel: EpoxyModelWithHolder<PokemonModel.ViewHolder>() {

    @EpoxyAttribute
    var name = "name"
    @EpoxyAttribute
    var url = "url"
    @EpoxyAttribute
    var listener: PokemonListener? = null

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        holder.itemView?.nameTextView?.text = name
        holder.itemView?.urlTextView?.text = url
        listener?.let { listener ->
            holder.itemView?.viewholderLayout?.setOnClickListener {
                listener.onClickListener(name)
            }
        }
    }

    class ViewHolder: EpoxyHolder() {

        var itemView: ViewholderPokemonBinding? = null

        override fun bindView(itemView: View) {
            this.itemView = ViewholderPokemonBinding.bind(itemView)

            Glide.with(itemView)
                .load("https://cdn.jpegmini.com/user/images/slider_puffin_before_mobile.jpg")
                .into(itemView.imageView)
        }
    }
}

interface PokemonListener {
    fun onClickListener(name: String)
}